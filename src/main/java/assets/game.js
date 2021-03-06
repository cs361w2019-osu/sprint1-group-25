var isSetup = true;
var placedShips = 0;
var game;
var shipType;
var vertical;
var submerged;
var isSonar;
var isDirection = false;
var direction = '';

function makeGrid(table, isPlayer) {
    for (i=0; i<10; i++) {
        let row = document.createElement('tr');
        for (j=0; j<10; j++) {
            let column = document.createElement('td');
            column.addEventListener("click", cellClick);
            row.appendChild(column);
        }
        table.appendChild(row);
    }
}

function markHits(board, elementId, surrenderText) {

    board.sonarSquares.forEach((attack) => {

        console.log(attack);

        let className;

        if (attack.result === "MISS")
            className = "placed";
        else if (attack.result === "HIT")
            className = "occupied";
        else if (attack.result === "SUNK")
            className = "occupied";
        else if (attack.result === "SURRENDER")
            alert(surrenderText);

        document.getElementById(elementId).rows[attack.location.row-1].cells[attack.location.column.charCodeAt(0) - 'A'.charCodeAt(0)].classList.add(className);
    });

    board.attacks.forEach((attack) => {

        let className;

        if (attack.result === "MISS")
            className = "miss";
        else if (attack.result === "HIT")
            className = "hit";
        else if (attack.result === "SUNK")
            className = "sink";
        else if (attack.result === "SURRENDER")
            alert(surrenderText);

        document.getElementById(elementId).rows[attack.location.row-1].cells[attack.location.column.charCodeAt(0) - 'A'.charCodeAt(0)].classList.add(className);
    });
}

function redrawGrid() {

    Array.from(document.getElementById("opponent").childNodes).forEach((row) => row.remove());
    Array.from(document.getElementById("player").childNodes).forEach((row) => row.remove());
    makeGrid(document.getElementById("opponent"), false);
    makeGrid(document.getElementById("player"), true);

    if (game === undefined) {
        return;
    }

    game.playersBoard.ships.forEach((ship) => ship.occupiedSquares.forEach((square) => {
        document.getElementById("player").rows[square.row-1].cells[square.column.charCodeAt(0) - 'A'.charCodeAt(0)].classList.add("occupied");
    }));

    markHits(game.opponentsBoard, "opponent", "You won the game");
    markHits(game.playersBoard, "player", "You lost the game");
}

var oldListener;
function registerCellListener(f) {
    let el = document.getElementById("player");
    for (i=0; i<10; i++) {
        for (j=0; j<10; j++) {
            let cell = el.rows[i].cells[j];
            cell.removeEventListener("mouseover", oldListener);
            cell.removeEventListener("mouseout", oldListener);
            cell.addEventListener("mouseover", f);
            cell.addEventListener("mouseout", f);
        }
    }
    oldListener = f;
}

function cellClick() {
    let row = this.parentNode.rowIndex + 1;
    let col = String.fromCharCode(this.cellIndex + 65);
    if (isSetup) {
        sendXhr("POST", "/place", {game: game, shipType: shipType, x: row, y: col, isVertical: vertical, isSubmerged: submerged}, function(data) {
            game = data;
            redrawGrid();
            placedShips++;

            if (shipType === "MINESWEEPER") {
                document.getElementById("place_minesweeper").style.backgroundColor = "#9e9e9e";
            }
            if (shipType === "BATTLESHIP") {
                document.getElementById("place_battleship").style.backgroundColor = "#9e9e9e";
            }
            if (shipType === "DESTROYER") {
                document.getElementById("place_destroyer").style.backgroundColor = "#9e9e9e";
            }
            if (shipType === "SUBMARINE") {
                document.getElementById("place_submarine").style.backgroundColor = "#9e9e9e";
                document.getElementsByClassName("submerge")[0].style.display = "none";
                if ( submerged ) { submerged = !submerged };
            }

            if (placedShips == 4) {
                isSetup = false;
                registerCellListener((e) => {});
                document.getElementsByClassName("buttons")[0].style.display = "none";
                document.getElementsByClassName("enemyboard")[0].style.display = "block";
                document.getElementById("move-wrapper").style.display = "block";
            }
        });
    } else {
        sendXhr("POST", "/attack", {game: game, x: row, y: col, isSonar: isSonar}, function(data) {
            game = data;
            if (isSonar) {
                isSonar = false;
                document.getElementById("sonar-wrapper").classList.toggle("sonar-on");
            }
            redrawGrid();
        })
    }
}

function sendXhr(method, url, data, handler) {
    var req = new XMLHttpRequest();
    req.addEventListener("load", function(event) {
        if (req.status != 200) {
            alert("Cannot complete the action");
            return;
        }
        handler(JSON.parse(req.responseText));
    });
    req.open(method, url);
    req.setRequestHeader("Content-Type", "application/json");
    req.send(JSON.stringify(data));
}

function place(size) {
    return function() {

        let row = this.parentNode.rowIndex;
        let col = this.cellIndex;

        vertical = document.getElementById("is_vertical").checked;
        submerged = document.getElementById("is_submerged").checked;

        let table = document.getElementById("player");

        for (let i=0; i<size; i++) {

            let cell;

            if( vertical ) {

                if(i == 4){
                    let tableRow = table.rows[row+2];
                    cell = tableRow.cells[col+1];
                } else {
                    let tableRow = table.rows[row+i];

                    if (tableRow === undefined) {
                        // ship is over the edge; let the back end deal with it
                        break;
                    }
                    cell = tableRow.cells[col];
                }
            } else {
                if(i == 4){
                   cell = table.rows[row-1].cells[col+2];
                } else {
                   cell = table.rows[row].cells[col+i];
                }
            }
            if (cell === undefined) {
                // ship is over the edge; let the back end deal with it
                break;
            }

            cell.classList.toggle("placed");
        }
    }
}

function moveFleet(direction) {
    console.log("asdfs") 
    sendXhr("POST", "/move", {game:game, direction: direction}, function(data) {
        game = data;
        redrawGrid();
    });
}

function initGame() {

    makeGrid(document.getElementById("opponent"), false);
    makeGrid(document.getElementById("player"), true);

    document.getElementById("place_minesweeper").addEventListener("click", function(e) {
        shipType = "MINESWEEPER";
        document.getElementsByClassName("submerge")[0].style.display = "none";
        registerCellListener(place(2));
    });
    document.getElementById("place_destroyer").addEventListener("click", function(e) {
        shipType = "DESTROYER";
        document.getElementsByClassName("submerge")[0].style.display = "none";
        registerCellListener(place(3));
    });
    document.getElementById("place_battleship").addEventListener("click", function(e) {
        shipType = "BATTLESHIP";
        document.getElementsByClassName("submerge")[0].style.display = "none";
        registerCellListener(place(4));
    });
    document.getElementById("place_submarine").addEventListener("click", function(e) {
        shipType = "SUBMARINE";
        document.getElementsByClassName("submerge")[0].style.display = "block";
        registerCellListener(place(5));
    });

    document.getElementsByClassName("sonar")[0].addEventListener("click", function(e) {
        isSonar = !isSonar;
        document.getElementById("sonar-wrapper").classList.toggle("sonar-on");
    });

    document.getElementsByClassName("north")[0].addEventListener("click", function(e) {
        moveFleet('n');
    });
    document.getElementsByClassName("east")[0].addEventListener("click", function(e) {
        moveFleet('e');
    });
    document.getElementsByClassName("south")[0].addEventListener("click", function(e) {
        moveFleet('s');
    });
    document.getElementsByClassName("west")[0].addEventListener("click", function(e) {
        moveFleet('w');
    });

    sendXhr("GET", "/game", {}, function(data) {
        game = data;
    });
};
