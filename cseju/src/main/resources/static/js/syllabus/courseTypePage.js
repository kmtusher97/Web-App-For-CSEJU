$(document).ready(function () {

    var syllabusName = $('#syllabusName').text();

    var loadDataFromXML = function () {
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

                    cellData = $('<button></button>').text('Edit');
                    cellData.attr('class', '');
                    cellData.attr('id', 'courseTypeEdit_' + i);
                    cellData.attr('onclick', 'openCourseStructureDesigner(' + '\'' + syllabusName + '\', \'' + courseTypeName + '\')');

                    var cell2 = document.createElement("td");
                    cellData.appendTo(cell2);

                    cellData = $('<a></a>').text('Delete');
                    cellData.attr('class', '');
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


    $('#addCourseBundleButton').on('click', function() {
        var courseTypeName = $('#courseTypeNameSelected').text();

        $.ajax({
                type: 'GET',
                url: '/courseStructure/Data/' + syllabusName + '/' + courseTypeName + '/add',
                success: function (result) {
                    loadCourseStructureDesignData(syllabusName, courseTypeName);
                },
                error: function (e) {
                    alert("Page Loading Error!!");
                    console.log("Error: ", e);
                }
        });
    });
})

/*load course structure data*/
function loadCourseStructureDesignData(syllabusName, courseTypeName) {
    console.log("ok");
    var loadCourseStructureDataFromXML = function () {
        $.ajax({
            type: 'GET',
            url: '/courseStructure/Data/' + syllabusName + '/' + courseTypeName,
            success: function (xmlString) {
                var xmlData = String(xmlString);
                var parser = new DOMParser();
                var xmlDoc = parser.parseFromString(xmlData, "text/xml");

                var i = 0;
                var contentBundlesXml = xmlDoc.getElementsByTagName('contentBundle');
                $(xmlString).find('contentBundle').each(function(index){

                    var selected = $(this).find('selected').text()

                    var row = $('<tr></tr>');
                    var cell = $('<td></td>');

                    var contentDiv = $('<div></div>');
                    contentDiv.attr('class', 'card');
                    contentDiv.attr('id', 'contentDiv_' + i);

                    /*option div*/
                    var optionDiv = $('<div></div>');
                    optionDiv.attr('class', 'fluid card');

                    var optionDiv1 = $('<div></div>');
                    optionDiv1.attr('class', 'fluid');

                    var selector = $('<select></select>');
                    selector.attr('class', 'btn btn-primary btn-sm float-right');
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
                    var j = 0;
                    var fieldNamesXml = xmlDoc.getElementsByTagName('fields')[i];

                    $(fieldNamesXml).find('field').each(function(index){
                        var fieldTd = $('<td></td>');
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

                        var deleteFieldNameButton = $('<a></a>').text('X');
                        deleteFieldNameButton.attr('class', 'btn btn-sm btn-danger btn-rounded float-right');
                        deleteFieldNameButton.attr('role', 'button');
                        deleteFieldNameButton.attr(
                            'href',
                            '/courseStructure/' + syllabusName + '/' + courseTypeName + '/' + i + '/deleteFieldName/' + j
                        );

                        deleteFieldNameButton.appendTo(fieldTdDiv);
                        fieldTdDiv.appendTo(fieldTd);
                        fieldNameRow.append(fieldTd);
                        j++;
                    });

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

                    var deleteContentButton = $('<a></a>').text('Delete');
                    deleteContentButton.attr('class', 'btn btn-danger btn-sm float-left');
                    deleteContentButton.attr('role', 'button');
                    deleteContentButton.attr(
                            'href',
                            '/courseStructure/' + syllabusName + '/' + courseTypeName + '/' + i
                    );
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
    }

    loadCourseStructureDataFromXML();
}

/*open and close course input form designer*/
var openCourseStructureDesigner = function(syllabusName, courseTypeName) {
    $('#courseTypesTableDiv').hide();
    $('#courseStructureDiv').show();

    $('#courseTypeNameSelected').text(courseTypeName);

    $('#courseContentTable tbody').empty();
    loadCourseStructureDesignData(syllabusName, courseTypeName);
};

var closeFormDesigner = function() {
    $('#courseTypesTableDiv').show();
    $('#courseStructureDiv').hide();
};


