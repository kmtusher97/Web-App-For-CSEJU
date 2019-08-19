
function loadFormData() {
    var courseContentTableRowCount =
            document.getElementById("courseContentTable").rows.length;

    var contentBundleList = [];

    for (var i = 0; i < courseContentTableRowCount - 1; i++) {
        var textArea = {
            title : $('#textArea_'.concat(i, '_field1Name')).val(),
            textBody : "Empty Text Body"
        };

        var fields = [];
        var countOfFieldNamesOfTable = document.getElementById(
                'table_'.concat(i, 'FieldNameTable')
        ).rows[0].cells.length;

        for (var j = 0; j < countOfFieldNamesOfTable; j++) {
            var tableContentFieldName = $('#contentTable'.concat(i, 'FieldName', j)).val();
            fields.push(tableContentFieldName);
        }

        var table = {
            title : $('#table_'.concat(i, '_field1Name')).val(),
            fields : fields,
            rows : []
        };

        var contentBundle = {
            selected : $('#contentSelector_'.concat(i)).val(),
            textArea : textArea,
            table : table
        };

        contentBundleList.push(contentBundle);
    }

    var courseStructure = {
        name : $("#courseStructureName").val(),
        databaseName : $("#databaseName").val(),
        contentBundleList : contentBundleList
    };

    return courseStructure;
}


function changeContentType(id) {
    var textArea = $("#textArea_".concat(id));
    var table = $("#table_".concat(id));

    var selectedContentType = $("#contentSelector_".concat(id)).val();

    if (selectedContentType == 1) {         // table selected
        textArea.hide();
        table.show();
    }
    else if (selectedContentType == 0) {  // textArea selected
        table.hide();
        textArea.show();
    }
}

$(document).ready(function() {

    $("#courseStructureDesignForm").on("change", function() {
        postViaAjax();
    });

    function postViaAjax() {
        var courseStructure = loadFormData();

        $.ajax({
            type : "POST",
            contentType : "application/json",
            url : "/autoSave/course_structure",
            data : JSON.stringify(courseStructure),
            dataType : 'json',
            success : function(result) {
                if (result.status == "saved") {
                    $("#statusText1")
                            .text("saving changes....")
                            .show()
                            .fadeOut(1000)
                            .css({
                                "color" : "blue"
                            });

                    $("#statusText2")
                            .delay(2500)
                            .text("saved!!!")
                            .show()
                            .fadeOut(2500)
                            .css({
                                "color" : "green"
                            });
                } else {
                    alert("Not saved");
                }
            },
            error : function(e) {
                alert("Error");
                console.log("Error: ", e);
            }
        });
    }
})