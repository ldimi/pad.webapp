
<jsp:useBean id="JsonMapper" scope="application" type="com.fasterxml.jackson.databind.ObjectMapper" />
<jsp:useBean id="__MODEL__" scope="request" type="java.util.Map" />

<%=JsonMapper.writeValueAsString(__MODEL__)%>




