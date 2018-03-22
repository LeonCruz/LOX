<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" media="screen" href="css/style-lojas.css" />
<title>Lojas</title>

<script type="text/javascript">
	function avaliar(idLoja, idCliente, avaliacao) {
		if(idCliente == -1) {
			window.location = "login.jsp";
		}
		
		var xhttp = new XMLHttpRequest();
		xhttp.onreadystatechange = function() {
			if (this.readyState == 4 && this.status == 200) {
		    	console.log("Enviado com sucesso!");
			}
		};
		xhttp.open("POST", "avalia.jsp", true);
		xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		xhttp.send("idLoja="+idLoja+"&idCliente="+idCliente+"&avaliacao="+avaliacao);
	}
</script>

</head>
<body>
	<header class="header-lojas">
		<a class="index" href="index.jsp">Home</a>
		
		<%		
			if(session.getAttribute("ClienteID") == null) {
				session.setAttribute("ClienteID", -1);
			}
		
			if(session.getAttribute("ClienteID").toString().equals("-1")) {
				%>
				<div class="login">
				<a href="login.jsp">Entrar</a>
				</div>
				<%
			} else {
				%>
				<div class="login">
				<span class="nome-user">Ol�, <%= session.getAttribute("ClienteNome") %></span>
				<a href="logout.jsp">Sair</a>
				</div>
				<%
			}
		%>
	</header>
	<main class="principal">
	
	<jsp:useBean class="modelo.Lojas" id="loja"/>
	
	<%@ 
		page import= "java.sql.ResultSet"
	%>
	
	<%
		int numTuplas = 0, idCliente=-1;
		String nomeLoja = request.getParameter("nome");
		ResultSet busca;
		
		busca = DAO.LojasDAO.buscarLojas(nomeLoja, request.getParameter("opcao"));
		
		busca.last();
		numTuplas = busca.getRow();
		busca.first();
		
		try{
			idCliente = Integer.parseInt(session.getAttribute("ClienteID").toString());
		} catch(NullPointerException e) {
			System.out.println(e);
		}
		
		if(numTuplas == 0) {
			%>
			<span class="error">Ops! Nenhuma loja foi encontrada :/</span>
			<%
		}
		
		for(int i=0; i<numTuplas; i++) {
	%>
	
		<div class="loja">
			<h2 class="titulo-loja"><%= busca.getString("nome") %></h2>
			<span class="endereco-loja"><%= busca.getString("localizacao") %></span> <br />
			
			<label for="gostei">Gostei</label>
			<input type="radio" id="gostei" name="avalia" onclick="avaliar(<%= busca.getInt("id") %>, <%= idCliente %>, this.value)" value="1">
			<div class="estrelas">Relev�ncia: <%= busca.getFloat("avaliacao") * 100 %>%</div>
			<label for="ngostei">N�o Gostei</label>
			<input type="radio" id="ngostei" name="avalia" onclick="avaliar(<%= busca.getInt("id") %>, <%= idCliente %>, this.value)" value="0">
			
			<div class="precos">
				
				<span class="preco">
					A�a� Fino R$<%= busca.getFloat("tipoFino") %>
				</span>
				<br />
				<span class="preco">
					A�a� M�dio R$<%= busca.getFloat("tipoMedio") %>
				</span>
				<br />
				<span class="preco">
					A�a� Grosso R$<%= busca.getFloat("tipoGrosso") %>
				</span>
			</div>
		</div>
		
		<%
			busca.next();
			} 
		%>
	</main>
</body>
</html>