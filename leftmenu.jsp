//THE FOLLOWING CODE IS REFRENCED IN EVERY PAGE AS IT CONTAINS THE LINK TO EVERY OPTIONS AND IT DISPLAYS THE OPTION DEPENDING ON THE TYPE OF USER I.E CA ,ADMIN AND USER
<%
    String utype = null;
    utype = (String) session.getAttribute("UTYPE");


%>

<div class="arrowgreen">
    <ul style="line-height: 20px;list-style: none;font-size: 14px;">


        <%            if (utype.equals("admin")) {
        %>           
        <li><a href="register_ca.jsp">Add CA</a></li>
        <li><a href="userlist.jsp">User List</a></li>
        <li><a href="adfilelists.jsp">File List</a></li>


        <%        } else if (utype.equals("user")) {
        %>         
        <li><a href="myaccount.jsp">My Account</a></li>
        <li><a href="uploadfile.jsp">Upload File</a></li>
        <li><a href="downloadfile.jsp">My Files</a></li>
        <li><a href="messages.jsp">Chat</a></li>



        <%            } else if (utype.equals("ca")) {
        %>         
        <li><a href="myaccount.jsp">My Account</a></li>

        <li><a href="downloadfileca.jsp">Files Shared</a></li>
  <li><a href="messages.jsp">Chat</a></li>
        <%            }
        %>
        <li><a href="changepwd.jsp">Change Password</a></li>
        <li><a href="logout">Logout</a></li>
    </ul>    
</div>
