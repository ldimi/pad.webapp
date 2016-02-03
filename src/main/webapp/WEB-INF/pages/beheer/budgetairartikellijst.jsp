<%@ page language="java" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/display-tags" prefix="display" %>
<table border="0">	
	<tr>		
		<td valign="top">
			<display:table id="budgetairartikel" name="applicationScope.DDH.budgetairartikel" requestURI="/budgetairartikellijstview.do" defaultsort="1" class="lijst width300">		
				<display:column property="artikel_b" title="Budgetair Artikel" href="budgetairartikel.do?crudAction=read" paramId="artikel_id" paramProperty="artikel_id"  sortable="true" />							
				<display:column>
					<logic:present role="adminArt46">
						<html:link action="budgetairartikel?crudAction=delete" paramId="artikel_id" paramName="budgetairartikel" paramProperty="artikel_id">
							<html:img border="0" page="/resources/images/delete.gif" title="Verwijderen budgetair artikel"/>
						</html:link>
					</logic:present>					 					
				</display:column>	
				<display:setProperty name="basic.msg.empty_list_row" value=""/>	
			</display:table>
		</td>	
		<td align="right" valign="top">
			<logic:present role="adminArt46">			
				<html:link action="budgetairartikel?crudAction=view&artikel_id=0">
					<html:img border="0" page="/resources/images/add.gif" title="Toevoegen budgetair artikel"/>
				</html:link>
			</logic:present>
		</td>	
	</tr>		
</table>