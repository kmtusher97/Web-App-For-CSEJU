
function loadFormData() {

    var contentCount =
            document.getElementById('contentsTable').rows.length;

    var contentList = [];

    for (var contentIndex = 0; contentIndex < contentCount; contentIndex++) {
        if ($('#tableDiv_'.concat(contentIndex)).val() == null) { /*if the content is a TextArea*/

            var textArea = {
                title: $('#textAreaTitle_'.concat(contentIndex)).text(),
                textBody: $('#textAreaTextBody_'.concat(contentIndex)).val()
            };

            contentList.push(textArea);
        } else {        /*if the content is a Table*/

            var table = {
                title: $('#tableTitle_'.concat(contentIndex)).text(),
                fields: [],
                rows: []
            };

            var fieldCount =
                    document.getElementById(
                            'inputTable_'.concat(contentIndex)
                    ).rows[1].cells.length;

            for (var fieldIndex = 0; fieldIndex < fieldCount - 1; fieldIndex++) {
                table.fields.push(
                    $('#table_'.concat(
                            contentIndex,
                            'fieldName_',
                            fieldIndex
                    )).text()
                );
            }

            var rowCount =
                    document.getElementById(
                            'inputTable_'.concat(contentIndex)
                    ).rows.length;

            for (var rowIndex = 0; rowIndex < rowCount - 3; rowIndex++) {
                var row = [];

                for (var colIndex = 0; colIndex < fieldCount - 1; colIndex++) {
                    row.push(
                            $('#table_'.concat(
                                    contentIndex,
                                    'cell_',
                                    rowIndex,
                                    '_',
                                    colIndex
                            )).val()
                    );
                }
                table.rows.push(row);
            }
            contentList.push(table);
        }
    }

    var contents = {
        contentList: contentList;
    };

    return contents;
}


$(document).ready(function() {

    $('#contentInputForm').on('change', function() {
        autoSaveChanges();
    });

    function autoSaveChanges() {
        var contents = loadFormData();
        var postUrl = '/autoSave/course/'.concat(
            $('#syllabusName').text(), '/',
            $('#yearId').text(), '/',
            $('#semesterId').text(), '/',
            $('#courseCode').text
        );

        console.log(postUrl);
        console.log(course);

        $.ajax({
            type: 'POST',
            contentType: 'application/json',
            url: postUrl,
            data : JSON.stringify(contents),
            dataType : 'json',
            success: function(result) {
                if (result.status === 'saved') {
                    $('#statusText')
                            .text('saved changes...')
                            .show()
                            .fadeOut(1000);
                } else {
                    alert('Not saved');
                }
            },
            error: function(e) {
                alert("Error");
                console.log("Error: ", e);
            }
        });
    }
})