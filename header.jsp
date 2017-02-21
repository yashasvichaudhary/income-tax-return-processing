<%
    String loginname = (String) session.getAttribute("NAME");
%>
<tr>
    <td colspan="3"  height="80"  align="left">
        <img src="images/logo.jpg" height="80px" width="150px"/>
        <h3>
            Income Tax Return
        </h3>
     
    </td>
</tr>
<tr>
    <td colspan="3" width="100%"  class="footer">
        <table border="0" width="100%" >
            
            
            <tr>
            
                <td class="white_font" width="70%" align="right" style="padding-right: 20px;">Welcome :<span class="yellow-font"><%=loginname%></span></td>
            </tr>
        </table>
    </td>
</tr>
