
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

                /**year row*/
                $(xmlString).find('year').each(function(index){
                    let yearId = $(this).attr('id');

                    let yearRow = $('<tr></tr>');
                    yearRow.attr('id', yearId);

                    let yearCell = $('<td></td>');

                    /**set row span*/
                    let rowSpanCount = function(xmlData) {
                        let rowSpan = 0;
                        $(xmlData).find('semester').each(function(index1){
                            let courseCount = $(this).find('course').length;
                            rowSpan += Math.max(1, courseCount);
                        });
                        return Math.max(1, rowSpan);
                    };
                    yearCell.attr(
                            'rowspan',
                            rowSpanCount($(this))
                    );

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
                        let semester1Cell = $('<td></td>');
                        semester1Cell.attr(
                            'rowspan',
                            Math.max(1, $($(this).find('semester')[0]).find('course').length)
                        );

                        let semester1Div = $('<div></div>');
                        semester1Div.attr('class', 'container');

                        let semesterId = $($($(this).find('semester'))[0]).attr('id');

                        let semesterName = $('<p></p>').text(
                                semesterId + getExtension(semesterId) + ' Semester'
                        );

                        semesterName.appendTo(semester1Div);

                        /**menu bar*/
                        let buttonGroup = $('<div></div>');
                        buttonGroup.attr('class', 'btn-group');
                        buttonGroup.attr('role', 'group');

                        /**add course button*/
                        let semesterActionBtn = $('<button>+ Course</button>').click(
                            {
                                syllabusName: syllabusName,
                                yearId: yearId,
                                semesterId: semesterId
                            },
                            function(ev) {
                                $.ajax({
                                        type: 'GET',
                                        url: '/syllabus/data/' + ev.data.syllabusName + '/' +
                                              ev.data.yearId + '/' + ev.data.semesterId + '/add/course',
                                        success: function(addCourseAcknowledgement) {
                                            loadSyllabus();
                                        },
                                        error: function(e) {
                                            alert("Failed to add course!!");
                                            console.log("Error: ", e);
                                        }
                                });
                            }
                        );
                        semesterActionBtn.attr('type', 'button');
                        semesterActionBtn.attr('class', 'btn btn-sm btn-success');
                        semesterActionBtn.appendTo(buttonGroup);

                        /**delete semester button*/
                        semesterActionBtn = $('<button>Delete Semester</button>').click(
                            {
                                syllabusName: syllabusName,
                                yearId: yearId,
                                semesterId: semesterId,
                                courseId: '101'///////////////////////////////////////////////////////
                            },
                            function(ev) {
                                $.ajax({
                                        type: 'GET',
                                        url: '/syllabus/data/' + ev.data.syllabusName + '/' +
                                              ev.data.yearId + '/' + ev.data.semesterId +
                                              '/delete/course' + ev.data.courseId,
                                        success: function(deleteCourseAcknowledgement) {
                                            loadSyllabus();
                                        },
                                        error: function(e) {
                                            alert("Failed to delete course!!");
                                            console.log("Error: ", e);
                                        }
                                });
                            }
                        );
                        semesterActionBtn.attr('type', 'button');
                        semesterActionBtn.attr('class', 'btn btn-sm btn-danger');
                        semesterActionBtn.appendTo(buttonGroup);

                        buttonGroup.appendTo(semester1Div);

                        semester1Div.appendTo(semester1Cell);
                        semester1Cell.appendTo(yearRow);
                    }


                    /**add year row in syllabusTable*/
                    $('#syllabusTable tbody').append(yearRow);

                    /**semesters from 2 to rest*/
                    if ($(this).find('semester').length > 1) {

                        $(this).find('semester').each(function(index1){

                            if (index1 != 0) {
                                let semesterRow = $('<tr></tr>');

                                let semesterCell = $('<td></td>');
                                let semesterDiv = $('<div></div>');
                                semesterDiv.attr('class', 'container');

                                let semesterId = $(this).attr('id');

                                let semesterName = $('<p></p>').text(
                                        semesterId + getExtension(semesterId) + ' Semester'
                                );

                                semesterName.appendTo(semesterDiv);

                                /**menu bar*/
                                let buttonGroup = $('<div></div>');
                                buttonGroup.attr('class', 'btn-group');
                                buttonGroup.attr('role', 'group');

                                /**add course button*/
                                let semesterActionBtn = $('<button>+ Course</button>').click(
                                    {
                                        syllabusName: syllabusName,
                                        yearId: yearId,
                                        semesterId: semesterId
                                    },
                                    function(ev) {
                                        $.ajax({
                                                type: 'GET',
                                                url: '/syllabus/data/' + ev.data.syllabusName + '/' +
                                                      ev.data.yearId + '/' + ev.data.semesterId + '/add/course',
                                                success: function(addCourseAcknowledgement) {
                                                    loadSyllabus();
                                                },
                                                error: function(e) {
                                                    alert("Failed to add course!!");
                                                    console.log("Error: ", e);
                                                }
                                        });
                                    }
                                );
                                semesterActionBtn.attr('type', 'button');
                                semesterActionBtn.attr('class', 'btn btn-sm btn-success');
                                semesterActionBtn.appendTo(buttonGroup);

                                /**delete semester button*/
                                semesterActionBtn = $('<button>Delete Semester</button>').click(
                                    {
                                        syllabusName: syllabusName,
                                        yearId: yearId,
                                        semesterId: semesterId,
                                        courseCode: '101'///////////////////
                                    },
                                    function(ev) {
                                        $.ajax({
                                                type: 'GET',
                                                url: '/syllabus/data/' + ev.data.syllabusName + '/' +
                                                      ev.data.yearId + '/' + ev.data.semesterId +
                                                      '/delete/course' + ev.data.courseCode,
                                                success: function(deleteCourseAcknowledgement) {
                                                    loadSyllabus();
                                                },
                                                error: function(e) {
                                                    alert("Failed to delete course!!");
                                                    console.log("Error: ", e);
                                                }
                                        });
                                    }
                                );
                                semesterActionBtn.attr('type', 'button');
                                semesterActionBtn.attr('class', 'btn btn-sm btn-danger');
                                semesterActionBtn.appendTo(buttonGroup);

                                buttonGroup.appendTo(semesterDiv);

                                semesterDiv.appendTo(semesterCell);
                                semesterCell.appendTo(semesterRow);

                                /**add semester row in syllabusTable*/
                                $('#syllabusTable tbody').append(semesterRow);
                            }
                        });
                    }
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