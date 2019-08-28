
$(document).ready(function() {

    var syllabusName = $('#syllabusName').text();

    var loadDataFromXML = function() {
        $.ajax({
        type: 'GET',
        url:  '/courseType/Data/' + syllabusName,
        success: function(xmlString) {
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
        error : function(e) {
            alert("Page Loading Error!!");
            console.log("Error: ", e);
        }
       });
    }

    loadDataFromXML();

    /*to add new course */
    $("#addCourseType").click(function() {
            $("#addTypeFormDivRow").show();
    });

    $("#addCourseTypeForm").submit(function() {
        var newCourseType = $("#newCourseTypeInput").val();
        for (var i = 0; i < $("#courseTypesTable tbody tr").length; i++) {
            var courseTypeName = $("#courseType_".concat(i)).text();
            if (courseTypeName == newCourseType) {
                $("#inputErrorMessage")
                                .text("Already Exists!!!")
                                .show()
                                .fadeOut(2500)
                                .css({
                                    "color" : "red"
                                });
                return false;
            }
        }
        return true;
    });
})

/*load course structure data*/
function loadCourseStructureDesignData(syllabusName, courseTypeName) {
    var loadCourseStructureDataFromXML = function() {
        $.ajax({
            type: 'GET',
            url:  '/courseStructure/Data/' + syllabusName + '/' + courseTypeName,
            success: function(xmlString) {
                var xmlData = String(xmlString);
                var parser = new DOMParser();
                var xmlDoc = parser.parseFromString(xmlData, "text/xml");

                var contentBundles = xmlDoc.getElementsByTagName('contentBundle');
                for (var i = 0; i < contentBundles.length; i++) {
                    var selected = xmlDoc.getElementsByTagName('selected')[i].childNodes[0].nodeValue;

                    var row = $('<tr></tr>');
                    var cell = $('<td></td>')

                    /*TextArea*/
                    if (selected == 0) {
                        var contentDiv = $('<div></div>');
                        contentDiv.attr('class', 'card');
                        contentDiv.attr('id', 'textArea_' + i);

                        var textAreaTitleInput = $('<input/>');
                        textAreaTitleInput.attr('class', '');
                        textAreaTitleInput.attr('id', 'textArea_' + i + 'title');
                        textAreaTitleInput.attr('type', 'text');
                        textAreaTitleInput.attr('value', 'Empty');

                        textAreaTitleInput.appendTo(contentDiv);

                        var textAreaTextBody = $('<textArea></textArea>');
                        textAreaTextBody.attr('class', '');
                        textAreaTextBody.attr('value', 'Input Here');

                        textAreaTextBody.appendTo(contentDiv);

                        cell.append(contentDiv);
                        row.append(cell);

                        $('#courseContentTable tbody').append(row);
                        /*<div class=""
                             th:id="'textArea_' + ${rowId.index}"
                             th:style="${contentBundle.selected == 1} ? 'display: none' : 'display: block'">

                            <div class=""
                                 th:id="'textArea_' + ${rowId.index} + '_field1'">

                                <input class=""
                                       type="text"
                                       th:id="'textArea_' + ${rowId.index} + '_field1Name'"
                                       th:value="${contentBundle.textArea.title}"/>
                            </div>

                            <div class=""
                                 th:id="'textArea_' + ${rowId.index} + '_field2Preview'">
                                <h3>Text Input Here...</h3>
                            </div>
                        </div>*/
                    }

                    //contentDiv.appendTo(cell);


                }
            },
            error : function(e) {
                alert("Page Loading Error!!");
                console.log("Error: ", e);
            }
        });
    }

    loadCourseStructureDataFromXML();
}

/*open and close course input form designer*/
function openCourseStructureDesigner(syllabusName, courseTypeName) {
    $('#courseTypesTableDiv').hide();
    $('#courseStructureDiv').show();

    loadCourseStructureDesignData(syllabusName, courseTypeName);
}

function closeFormDesigner() {
    $('#courseTypesTableDiv').show();
    $('#courseStructureDiv').hide();
};