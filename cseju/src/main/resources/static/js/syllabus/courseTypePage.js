$.hiddenSection1 = new Object();
$.hiddenSection2 = new Object();

$(document).ready(function () {


    var syllabusName = $('#syllabusName').text();

    var loadDataFromXML = function () {
        /*$('#courseTypesTableDiv').show();
        $('#courseStructureDiv').hide();*/
        hiddenSection1 = $('#sec1');
        hiddenSection2 = $('#sec2');
        $('#sec2').remove();

        $.ajax({
            type: 'GET',
            url: '/courseType/Data/' + syllabusName,
            success: function (xmlString) {
                var xmlData = String(xmlString);
                var parser = new DOMParser();
                var xmlDoc = parser.parseFromString(xmlData, "text/xml");

                var courseTypes = xmlDoc.getElementsByTagName('courseType');
                for (var i = 0; i < courseTypes.length; i++) {
                    var courseTypeName = courseTypes[i].getAttribute('name');

                    var row = document.createElement('tr');

                    var cell0 = document.createElement("td");
                    cell0.innerHTML = i + 1;

                    var cellData = $('<h6 id="courseType_' + i + '"></h6>').text(courseTypeName);
                    var cell1 = document.createElement("td");
                    cellData.appendTo(cell1);

                    cellData = $('<button>Edit</button>');
                    cellData.attr('class', 'btn btn-primary btn-sm');
                    cellData.attr('id', 'courseTypeEdit_' + i);
                    cellData.attr('onclick', 'openDesigner(\'' + courseTypeName + '\')');

                    var cell2 = document.createElement("td");
                    cellData.appendTo(cell2);

                    cellData = $('<a></a>').text('Delete');
                    cellData.attr('class', 'btn btn-danger btn-sm');
                    cellData.attr('id', 'courseTypeDelete_' + i);
                    cellData.attr('role', 'button');
                    cellData.attr('href', '/courseType/' + syllabusName + '/deleteType/' + courseTypeName);

                    var cell3 = document.createElement("td");
                    cellData.appendTo(cell3);


                    row.append(cell0, cell1, cell2, cell3);
                    $('#courseTypesTable tbody').append(row);
                }
            },
            error: function (e) {
                alert("Page Loading Error!!");
                console.log("Error: ", e);
            }
        });
    }

    loadDataFromXML();

    /*to add new course */
    $("#addCourseType").click(function () {
        $("#addTypeFormDivRow").show();
    });

    $("#addCourseTypeForm").submit(function () {
        var newCourseType = $("#newCourseTypeInput").val();
        for (var i = 0; i < $("#courseTypesTable tbody tr").length; i++) {
            var courseTypeName = $("#courseType_".concat(i)).text();
            if (courseTypeName == newCourseType) {
                $("#inputErrorMessage")
                    .text("Already Exists!!!")
                    .show()
                    .fadeOut(2500)
                    .css({
                        "color": "red"
                    });
                return false;
            }
        }
        return true;
    });

})


/*open course input form designer*/
var openDesigner = function(courseTypeName) {
    $('#sec1').replaceWith(hiddenSection2);
    $('#courseTypeNameSelected').text(courseTypeName);
    $('#courseContentTable tbody').empty();
    loadCourseStructureDesignData($('#syllabusName').text(), courseTypeName);
};

/*close course input form designer*/
var closeDesigner = function() {
    $('#sec2').replaceWith(hiddenSection1);
};


/*add new contentBundle*/
var addContentBundle = function() {
    var syllabusName = $('#syllabusName').text();
    var courseTypeName = $('#courseTypeNameSelected').text();
    console.log(syllabusName + ', ' + courseTypeName);

    $.ajax({
            type: 'GET',
            url: '/courseStructure/Data/' + syllabusName + '/' + courseTypeName + '/add',
            success: function (result) {
                $('#courseContentTable tbody').empty();
                loadCourseStructureDesignData(syllabusName, courseTypeName);
            },
            error: function (e) {
                alert("Page Loading Error!!");
                console.log("Error: ", e);
            }
    });
};

/*load course structure data*/
var loadCourseStructureDesignData = function(syllabusName, courseTypeName) {

    $.ajax({
        type: 'GET',
        url: '/courseStructure/Data/' + syllabusName + '/' + courseTypeName,
        success: function (xmlString) {
            var xmlData = String(xmlString);
            var parser = new DOMParser();
            var xmlDoc = parser.parseFromString(xmlData, "text/xml");

            var i = 0;

            $(xmlString).find('contentBundle').each(function(index){

                var selected = $(this).find('selected').text()

                var row = $('<tr></tr>');
                row.attr('id', 'contentRow_' + i);
                var cell = $('<td></td>');

                var contentDiv = $('<div></div>');
                contentDiv.attr('class', 'card');
                contentDiv.attr('id', 'contentDiv_' + $(this).attr('id'));

                /*option div*/
                var optionDiv = $('<div></div>');
                optionDiv.attr('class', 'fluid card');
                optionDiv.css('background-color','#ccffff');

                var optionDiv1 = $('<div></div>');
                optionDiv1.attr('class', 'fluid');

                var selector = $('<select></select>').on(
                        'change',
                        {id: i},
                        function(event) {
                            if ($('#selector_' + event.data.id).val() == 1) {
                                $('#textArea_' + event.data.id).hide();
                                $('#table_' + event.data.id).show();
                            } else {
                                $('#textArea_' + event.data.id).show();
                                $('#table_' + event.data.id).hide();
                            }
                        }
                );
                selector.attr('id', 'selector_' + i);
                selector.attr('class', 'btn btn-primary btn-sm float-right');
                /*selector.attr('onchange', 'changeContentType(\'' + i + '\')');*/

                var selectorOption = $('<option></option>').text('TextArea');
                selectorOption.attr('value', '0');
                if (selected == 0) {
                    selectorOption.prop('selected', 'selected');
                }
                selector.append(selectorOption);

                selectorOption = $('<option></option>').text('Table');
                selectorOption.attr('value', '1');
                if (selected == 1) {
                    selectorOption.prop('selected', 'selected');
                }
                selector.append(selectorOption);

                selector.appendTo(optionDiv1);

                optionDiv1.appendTo(optionDiv);
                optionDiv.appendTo(contentDiv);


                /*TextArea*/
                var textAreaDiv = $('<div></div>')
                textAreaDiv.attr('class', 'card');
                textAreaDiv.attr('id', 'textArea_' + i);
                if (selected != 0) {
                    textAreaDiv.attr('style', 'display:none');
                }

                var textAreaTitleInput = $('<input/>');
                textAreaTitleInput.attr('class', 'form-control');
                textAreaTitleInput.attr('id', 'textArea_' + i + 'title');
                textAreaTitleInput.attr('type', 'text');
                if (selected != 0) {
                    textAreaTitleInput.attr('value', 'Untitled TextArea');
                } else {
                    var textAreaXml = xmlDoc.getElementsByTagName('textArea')[i];

                    textAreaTitleInput.attr(
                            'value',
                            $(textAreaXml).find('title').text()
                    );
                }

                textAreaTitleInput.appendTo(textAreaDiv);

                var textAreaTextBody = $('<textArea></textArea>').text('Input Here');
                textAreaTextBody.attr('class', 'form-control');

                textAreaTextBody.appendTo(textAreaDiv);
                textAreaDiv.appendTo(contentDiv);

                /*table*/
                var tableDiv = $('<div></div>');
                tableDiv.attr('class', 'card');
                tableDiv.attr('id', 'table_' + i);
                if (selected != 1) {
                    tableDiv.attr('style', 'display:none');
                }

                var tableTitleInput = $('<input/>');
                tableTitleInput.attr('class', 'form-control');
                tableTitleInput.attr('id', 'table_' + i + 'title');
                tableTitleInput.attr('type', 'text');

                var tableXml = xmlDoc.getElementsByTagName('table')[i];
                if (selected != 1) {
                    tableTitleInput.attr('value', 'Untitled Table');
                } else {
                    tableTitleInput.attr(
                            'value',
                            $(tableXml).find('title').text()
                    );
                }
                tableTitleInput.appendTo(tableDiv);

                var tableFieldNameTable = $('<table></table>');
                tableFieldNameTable.attr('class', 'table table-bordered');
                tableFieldNameTable.attr('id', 'tableFieldNameTable_' + i);

                var fieldNameRow = $('<tr></tr>');
                var fieldNames = xmlDoc.getElementsByTagName('fields')[i];

                /**table field names */
                var contentBundleId = $(this).attr('id');
                var j = 0;
                var fieldNamesXml = xmlDoc.getElementsByTagName('fields')[i];

                $(fieldNamesXml).find('field').each(function(index){
                    var fieldTd = $('<td></td>');
                    fieldTd.attr('id', 'table_' + i + 'field_' + j);

                    var fieldTdDiv = $('<div></div>');
                    fieldTdDiv.attr('class', 'fluid');

                    var fieldNameInput = $('<input/>');
                    fieldNameInput.attr('class', 'form-control');
                    fieldNameInput.attr('id', 'table_' + i + 'fieldName_' + j);

                    if (selected != 1) {
                        fieldNameInput.attr('value', 'field ' + j);
                    } else {
                        fieldNameInput.attr(
                                'value',
                                $(this).text()
                        );
                    }

                    fieldNameInput.appendTo(fieldTd);

                    /*delete field from table*/
                    var deleteFieldNameButton = $('<p><span class="glyphicon glyphicon-trash"></p>').click(
                        {
                            syllabusName: syllabusName,
                            courseTypeName: courseTypeName,
                            id: contentBundleId,
                            rowId: i,
                            fieldId: j
                        },
                        function(ev) {
                            console.log('/courseStructure/Data/' + ev.data.syllabusName + '/' +
                                                                                 ev.data.courseTypeName + '/deleteField/' + ev.data.id + '/' + ev.data.fieldId);
                            $.ajax({
                                    type: 'GET',
                                    url: '/courseStructure/Data/' + ev.data.syllabusName + '/' +
                                            ev.data.courseTypeName + '/deleteField/' + ev.data.id + '/' + ev.data.fieldId,
                                    success: function (result) {
                                        $('#table_' + ev.data.rowId + 'field_' + ev.data.fieldId).remove();
                                    },
                                    error: function (e) {
                                        alert("Failed to delete the field!!");
                                        console.log("Error: ", e);
                                    }
                            });
                        }
                    );
                    deleteFieldNameButton.attr('class', 'float-right');
                    deleteFieldNameButton.attr('role', 'button');


                    deleteFieldNameButton.appendTo(fieldTdDiv);
                    fieldTdDiv.appendTo(fieldTd);
                    fieldNameRow.append(fieldTd);
                    j++;
                });

                /*add field name button*/
                var fieldTd = $('<td></td>');
                var addFieldButton = $('<p><span class="glyphicon glyphicon-plus"></p>').click(
                        {
                            syllabusName: syllabusName,
                            courseTypeName: courseTypeName,
                            id: $(this).attr('id')
                        },
                        function(ev) {
                            $.ajax({
                                type: 'GET',
                                url: '/courseStructure/Data/' + ev.data.syllabusName + '/' + ev.data.courseTypeName +
                                        '/addField/' + ev.data.id,
                                success: function (result) {
                                    $('#courseContentTable tbody').empty();
                                    loadCourseStructureDesignData(syllabusName, courseTypeName);
                                },
                                error: function (e) {
                                    alert("Field Not Added!!");
                                    console.log("Error: ", e);
                                }
                            });
                        }
                );
                addFieldButton.attr('class', 'btn btn-success btn-sm');

                addFieldButton.appendTo(fieldTd);
                fieldNameRow.append(fieldTd);

                tableFieldNameTable.append(fieldNameRow);

                var tableFieldNameTableDiv = $('<div></div>');
                tableFieldNameTableDiv.attr('class', 'fluid card');
                tableFieldNameTable.appendTo(tableFieldNameTableDiv);
                tableFieldNameTableDiv.appendTo(tableDiv);

                tableDiv.appendTo(contentDiv);

                /*delete content bundle*/
                var deleteContentDiv = $('<div></div>');
                deleteContentDiv.attr('class', 'fluid');

                var deleteContentDiv1 = $('<div></div>');
                deleteContentDiv1.attr('class', 'fluid');

                var deleteContentButton = $('<p><span class="glyphicon glyphicon-trash"></p>').click(
                    {
                        syllabusName: syllabusName,
                        courseTypeName: courseTypeName,
                        id: $(this).attr('id'),
                        rowId: i
                    },
                    function(ev) {
                        $.ajax({
                                type: 'GET',
                                url: '/courseStructure/Data/' + ev.data.syllabusName + '/' +
                                        ev.data.courseTypeName + '/deleteContentBundle/' + ev.data.id,
                                success: function (result) {
                                    $('#contentRow_' + ev.data.rowId).remove();
                                },
                                error: function (e) {
                                    alert("Page Loading Error!!");
                                    console.log("Error: ", e);
                                }
                        });
                    }
                );
                deleteContentButton.attr('class', 'btn btn-danger btn-sm float-left');
                deleteContentButton.attr('id', 'contentDeleteButton_' + i);

                deleteContentButton.appendTo(deleteContentDiv1);
                deleteContentDiv1.appendTo(deleteContentDiv)
                deleteContentDiv.appendTo(contentDiv);

                cell.append(contentDiv);
                row.append(cell);

                $('#courseContentTable tbody').append(row);

                i++;
            });
        },
        error: function (e) {
            alert("Page Loading Error!!");
            console.log("Error: ", e);
        }
    });
};

