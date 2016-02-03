
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

	
<br>
<br>
<h1>Status databank</h1>
<br>
<%-- Totaal Adresrecords<br>${aantalAdresRecords}<br> --%>
<%-- Totaal actieve Adresrecords<br>>${aantalActieveAdresRecords}<br> --%>
<!-- Totaal dubbele(straatnaam en postcode zijn identiek) actieve Adresrecords<br><br> -->
<!-- Totaal Correcte actieve adresrecords<br><br> -->
<!-- <br><br><br><br><br><br><br><br><br><br> -->
<!-- <br><br><br><br><br><br><br><br><br><br><br><br><br><br> -->
<%-- <h2>totaal aantal actieve adres records:<b>${aantalAdresRecords}</b></h2> --%>
<!-- via query:  -->
<!-- <br> -->
<!--  <i>select  -->
<!--  		count(id) -->
<!--     from  -->
<!--     	ART46.ADRES_1 -->
<!--     	where (teverwijderen != '1'  -->
<!--    		or teverwijderen is null)</i> -->

<!-- <br> -->
<%-- <h2>totaal aantal rechtspersoon records:<b>${aantalRPRecords}</b></h2> --%>
<!-- <i> select  -->
<!--  		count(id) -->
<!--     from  -->
<!--     	ART46.Rechtspersoon -->
<!--     	</i><br> -->
<%-- <h2>aantal dubbele records op basis van naam:<b>${dubbelerecordsOpNaam}</b></h2> --%>
<!-- <i>select -->
<!-- 	sum(doubles) -->
<!-- from( -->
<!-- 	select  -->
<!-- 		count( distinct rp1.id) as doubles -->
<!-- 	from art46.RECHTSPERSOON rp1,art46.RECHTSPERSOON rp2 -->
<!-- 	where rp1.NAAM = rp2.NAAM AND rp1.NAAM2=rp2.NAAM2 -->
<!-- 	AND rp1.id !=rp2.id -->
<!-- UNION ALL -->
<!-- 	select  -->
<!-- 		count( distinct rp1.id) as doubles -->
<!-- 	from art46.RECHTSPERSOON rp1,art46.RECHTSPERSOON rp2 -->
<!-- 	where rp1.NAAM2 = rp2.NAAM AND rp1.NAAM=rp2.NAAM2 -->
<!-- 	AND rp1.id !=rp2.id -->
<!-- )</i> -->
<!-- <br> -->
<%-- <h2>aantal actieve, dubbele adresrecords:<b>${dubbeleAdressen}</b></h2>   --%>
<!-- <br>-> dit is maar een indicatie, gezien er vele "null" -->
<!-- <br> -->
<!-- <br> -->
<%-- aantal onvolledige adres records:${onvolledigeAdressen}<br> --%>
<!-- <br> -->

<%-- <form action="/rechtspersoon/zoeken" method="GET"> --%>
<!-- 	<button>GO zoeken</button> -->
<%-- </form> --%>


  