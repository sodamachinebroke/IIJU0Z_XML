 // Load the XML data from an external file
 var xmlhttp = new XMLHttpRequest();
 xmlhttp.open("GET", "iiju0z_orarend.xml", false);
 xmlhttp.send();
 var xmlDoc = xmlhttp.responseXML;

 // Get all "ora" elements
 var orarendData = xmlDoc.querySelectorAll("ora");

 // Create the table and table rows
 var table = document.getElementById("orarend-table");

 for (var i = 0; i < orarendData.length; i++) {
   var row = table.insertRow(i + 1);

   var tipus = orarendData[i].getAttribute("tipus");
   var targy = orarendData[i].querySelector("targy").textContent;
   var nap = orarendData[i].querySelector("idopont > nap").textContent;
   var tol = orarendData[i].querySelector("idopont > tol").textContent;
   var ig = orarendData[i].querySelector("idopont > ig").textContent;
   var helyszin = orarendData[i].querySelector("helyszin").textContent;
   var oktato = orarendData[i].querySelector("oktato").textContent;
   var szak = orarendData[i].querySelector("szak").textContent;

   var cells = [tipus, targy, nap, tol, ig, helyszin, oktato, szak];

   for (var j = 0; j < cells.length; j++) {
     var cell = row.insertCell(j);
     cell.innerHTML = cells[j];
     if (j === 3 || j === 4) {
       cell.classList.add("center-align");
     }
   }
 }