
$(document).ready(function() {

    var getXML = function() {
        $.ajax({
        type: 'GET',
        url:  '/test/getAllCt/undergrad_2018-2022',
        success: function(result) {
            var xmlData = '<courseTypes>' + String(result) + '</courseTypes>';
            var parser = new DOMParser();
            var xmlDoc = parser.parseFromString(xmlData, "text/xml");

            console.log(xmlData);
            x = xmlDoc.getElementsByTagName('courseType');
            for (i = 0; i < x.length; i++) {
                console.log(x[i].getAttribute('name'));

                var para = document.createElement("p");
                var node = document.createTextNode(x[i].getAttribute('name'));
                para.appendChild(node);
                var att = document.createAttribute("id");
                att.value = i + 1;
                para.setAttributeNode(att);

                var element = document.getElementById("mainDiv");
                element.appendChild(para);
            }
        },
        error : function(e) {
            alert("Error");
            console.log("Error: ", e);
        }
       });
    }

    getXML();
})