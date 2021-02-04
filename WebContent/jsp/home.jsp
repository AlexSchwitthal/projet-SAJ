<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <%@include file="header.jsp" %>
    <body>
         <div class="container">
	        <div id="menu">	        		
	        		 <div id="includeMenu">
	        				<jsp:include page="menu.jsp" />
	        			</div>          
					<section class="dark-wrapper opaqued parallax" data-parallax="scroll" data-image-src="assets/img/Relation_medecin-patient.jpg" data-speed="0.7">	
						 						
				            <div class="section-inner ">				            	
				                <div class="container">
				                	
				                    <div class="row">				                    	
				                        <div class="col-lg-12 mt30 wow text-center">
				                            <h2 class="section-heading">Bienvenue <%= request.getAttribute("login") %></h2>
				                            
				                        </div>
				                    </div>
				                     
				                </div>
				               
				            </div>
				     </section>
				</div>	
			</div>	
    </body>
</html>

