<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
  <title>users</title>
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/tables.css">
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/add.css">
</head>
<body>
<h1>Список постачальників:</h1>
<div class="buttons">
  <button id="add-data" onclick="addFields()">Add data</button>
  <button id="update-data" onclick="addFields2()">Update data</button>
  <button id="delete-data" onclick="addFields1()">Delete data</button>
</div>

<c:if test="${requestScope.check == 1}">
  <div id="forms_to_update">
    <form id="form3" action="<c:url value="/suppliers"/>" method="POST">
      <div class="textboxes">
        <label for="field10">companyName :</label>
        <input type="text" id="field10" name="field10" value="${Supplier.companyName}"/><br>
        <label for="field11">contactPerson :</label>
        <input type="text" id="field11" name="field11" value="${Supplier.contactPerson}"/><br>
        <label for="field12">address :</label>
        <input type="text" id="field12" name="field12" value="${Supplier.address}"/><br>
        <label for="field13">email :</label>
        <input type="text" id="field13" name="field13" value="${Supplier.email}"/><br>
      </div>
      <input id="submit-button" type="submit" value="Submit" name="action"/>
    </form>
  </div>
</c:if>

<div id="form-container" style="display: none;">
  <form id="form" action="<c:url value="/suppliers"/>" method="POST">
    <div class="textboxes">
      <label for="field1">id :</label>
      <input type="text" id="field1" name="field1"/><br>
      <label for="field2">companyName :</label>
      <input type="text" id="field2" name="field2"/><br>
      <label for="field3">contactPerson :</label>
      <input type="text" id="field3" name="field3"/><br>
      <label for="field4">address :</label>
      <input type="text" id="field4" name="field4"/><br>
      <label for="field5">email :</label>
      <input type="text" id="field5" name="field5"/><br>
    </div>
    <input id="save-button" type="submit" value="Save" name="action"/>
  </form>
</div>
<br>
<div id="delete_boxes" style="display: none;">
  <form id="form1" action="<c:url value="/suppliers"/>" method="POST">
    <label for="dfield">id :</label>
    <input type="text" id="dfield" name="dfield"/>
    <div><input id="delete-commit" type="submit" value="Delete" name="action"/></div>
  </form>
</div>
<br>
<div id="update_box" style="display: none;">
  <form id="form2" action="<c:url value="/suppliers"/>" method="POST">
    <label for="ufield">id :</label>
    <input type="text" id="ufield" name="ufield"/>
    <div><input id="update-commit" type="submit" value="Update" name="action"/></div>
  </form>
</div>

<script>
  //кнопка add
  function addFields() {
    var container = document.getElementById("form-container");
    container.style.display = "block";
    document.getElementById("add-data").style.display = "none";
    document.getElementById("form").onsubmit = function () {
      container.style.display = "none";
      document.getElementById("add-data").style.display = "block";
      return true;
    };
  }
  function addFields1() {
    var container = document.getElementById("delete_boxes");
    container.style.display = "block";
    document.getElementById("delete-data").style.display = "none";
    document.getElementById("form1").onsubmit = function () {
      container.style.display = "none";
      document.getElementById("delete-data").style.display = "block";
      return true;
    };
  }
  function addFields2() {
    var container = document.getElementById("update_box");
    container.style.display = "block";
    document.getElementById("update-data").style.display = "none";
    document.getElementById("form2").onsubmit = function () {
      container.style.display = "none";
      document.getElementById("update-data").style.display = "block";
      return true;
    };
  }
</script>
<br>
<table>
  <tr>
    <th>id</th>
    <th>companyName</th>
    <th>contactPerson</th>
    <th>address</th>
    <th>email</th>
  </tr>
  <c:forEach items="${allSuppliers}" var="sp">
    <tr>
      <td><c:out value="${sp.id}"/></td>
      <td><c:out value="${sp.companyName}"/></td>
      <td><c:out value="${sp.contactPerson}"/></td>
      <td><c:out value="${sp.address}"/></td>
      <td><c:out value="${sp.email}"/></td>
    </tr>
  </c:forEach>
</table>
</body>
</html>