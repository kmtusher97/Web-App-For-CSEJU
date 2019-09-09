
$(document).ready(function() {
    var syllabusName = $('#syllabusName').text();

    var getExtension = function(n) {
        if (n % 4 == 1) return 'st';
        if (n % 4 == 2) return 'nd';
        if (n % 4 == 3) return 'rd';
        return 'th';
    };

    var loadSyllabus = function() {
        $('#syllabusTable tbody').empty();

        $.ajax({
            type: 'GET',
            url: '/syllabus/data/get/' + syllabusName,
            success: function(xmlString) {
                console.log(xmlString);

                let yearList = [];
                $(xmlString).find('year').each(function(index){
                    let yearId = $(this).attr('id');

                    let yearRow = $('<tr></tr>');
                    yearRow.attr('id', yearId);

                    let yearCell = $('<td></td>');

                    let yearDiv = $('<div></div>');
                    yearDiv.attr('class', 'container');

                    let yearName = $('<p></p>').text(
                            yearId + getExtension(yearId) + ' Year'
                    );

                    let buttonGroup = $('<div></div>');
                    buttonGroup.attr('class', 'btn-group');
                    buttonGroup.attr('role', 'group');

                    let yearActionBtn = $('<button>+ Semester</button>').click(
                            {
                                syllabusName: syllabusName,
                                yearId: yearId
                            },
                            function(ev) {
                                $.ajax({
                                        type: 'GET',
                                        url: '/syllabus/data/' + ev.data.syllabusName + '/' +
                                              ev.data.yearId + '/add/semester',
                                        success: function(semesterAddAcknowledgement) {
                                            loadSyllabus();
                                        },
                                        error: function(e) {
                                            alert("Failed to add semester!!");
                                            console.log("Error: ", e);
                                        }
                                });
                            }
                    );
                    yearActionBtn.attr('type', 'button');
                    yearActionBtn.attr('class', 'btn btn-sm btn-success');
                    yearActionBtn.attr('id', 'addSemester_' + index);
                    yearActionBtn.appendTo(buttonGroup);

                    yearActionBtn = $('<button>Delete Year</button>').click(
                            {
                                syllabusName: syllabusName,
                                yearId: yearId
                            },
                            function(ev) {
                                $.ajax({
                                        type: 'GET',
                                        url: '/syllabus/data/' + ev.data.syllabusName +
                                                '/deleteYear/' + ev.data.yearId,
                                        success: function(deleteAcknowledgement) {
                                            loadSyllabus();
                                        },
                                        error: function(e) {
                                            alert("Failed to delete the Year!!");
                                            console.log("Error: ", e);
                                        }
                                });
                            }
                    );
                    yearActionBtn.attr('type', 'button');
                    yearActionBtn.attr('class', 'btn btn-sm btn-danger');
                    yearActionBtn.attr('id', 'deleteYear_' + index);
                    yearActionBtn.appendTo(buttonGroup);

                    yearName.appendTo(yearDiv);
                    buttonGroup.appendTo(yearDiv);
                    yearDiv.appendTo(yearCell);
                    yearCell.appendTo(yearRow);

                    /**view first semester*/
                    if ($(this).find('semester').length > 0) {
                        console.log($($($(this).find('semester'))[0]).attr('id'));
                        let semester1Cell = $('<td></td>');
                        let semester1Div = $('<div></div>');
                        semester1Div.attr('class', 'container');

                        let semesterId = $($($(this).find('semester'))[0]).attr('id');

                        let semesterName = $('<p></p>').text(
                                semesterId + getExtension(yearId) + ' Year'
                        );
                    }



                    $('#syllabusTable tbody').append(yearRow);
                });
            },
            error: function(e) {
                alert('Page Loading Error!!');
                console.log("Error: ", e);
            }
        });
    };

    loadSyllabus();

    $('#addYear').on('click', function() {
        $.ajax({
            type: 'GET',
            url: '/syllabus/data/' + syllabusName + '/add/year',
            success: function(addResponse) {
                loadSyllabus();
            },
            error: function(e) {
                alert('Year not added!!');
                console.log("Error: ", e);
            }
        });
    });
});