function initEditor(divID, width, height) {
    if (window.__initEditor) {
        document.getElementById(divID).style.width = width + "px";
        document.getElementById(divID).style.height = height + "px";
        document.getElementById(divID).innerHTML = "";
        __initEditor(divID, width, height);
    } else {
        document.getElementById(divID).style.width = width + "px";
        document.getElementById(divID).style.height = height + "px";       
        document.getElementById(divID).innerHTML = "<center>Loading...</center>";
        setTimeout(function() {
            initEditor(divID, width, height);
        }, 1000);
    }
}
function importMolFile(divID, fileContent) {
    if (window.__importMolFile) {
        __importMolFile(divID, fileContent);
    } else {
        setTimeout(function() {
            importMolFile(divID, fileContent);
        }, 1000);
    }
}

function exportMolFile(divID) {
    if (window.__exportMolFile) {
        __exportMolFile(divID);
    } else {
        setTimeout(function() {
            exportMolFile(divID);
        }, 1000);
    }
}

