
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
                    yearDiv.attr('class', '');

                    let yearName = $('<p></p>').text(
                            yearId + getExtension(yearId) + ' Year'
                    );

                    yearName.appendTo(yearDiv);
                    yearDiv.appendTo(yearCell);
                    yearCell.appendTo(yearRow);

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