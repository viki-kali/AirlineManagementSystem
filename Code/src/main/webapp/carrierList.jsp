<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.model.CarrierDetails" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Carrier</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <style>
        body {
            font-family: Arial, sans-serif;
            color: #333;
            margin: 0;
            padding: 0;
            background-color: #f8f9fa;
            display: flex;
            flex-direction: column;
            min-height: 100vh;
        }

        .content {
            flex: 1;

        }

        .table-container {
            max-height: 500px;
            overflow-y: auto;
            margin-top: 10px;
        }

        .table {
            width: 100%;
            border-collapse: collapse;
        }

        .table th {
            background-color: #343a40;
            color: white;
            text-transform: uppercase;
            border: 1px solid #dee2e6;
            padding: 10px;
            text-align: left;
        }

        .table td {
            border: 1px solid #ddd;
            padding: 10px;
            text-align: left;
        }

        .table tbody tr:hover {
            background-color: #f2f2f2;
        }

        .btn-edit, .btn-delete {
            padding: 8px 12px;
            border: none;
            cursor: pointer;
            border-radius: 4px;
            color: white;
        }

        .btn-edit {
            background-color: #5cb85c;
        }

        .btn-delete {
            background-color: #d9534f;
        }

        .add-carrier-button {
            text-align: center;
        }

        button {
            margin: 7px;
            width: 120px;
        }

        .add-new {
            width: 150px;
        }

        #myInput {
            width: 20%;
            font-size: 16px;
            padding: 12px 20px 12px 40px;
            border: 1px solid #ddd;
            margin-bottom: 10px;
        }

        .footer {
            background-color: #343a40;
            color: white;
            text-align: center;
            padding: 10px 0;
            position: fixed;
            width: 100%;
            bottom: 0;
            left: 0;
        }
    </style>
</head>
<body>


    <jsp:include page="adminheader.jsp" />

    <div class="content">
        <h2 class="text-center mt-3">List of Carriers</h2>
        
        <div class="add-carrier-button">
            <button class="btn btn-primary add-new" onclick="window.location.href='CarrierServlet?action=new'">
                Add New Carrier
            </button>
        </div>
        
        <input type="text" id="myInput" onkeyup="myFunction()" placeholder="Search for Carrier ID..." title="Type in a name">

        <div class="table-container">
            <table class="table" id="myTable">
                <tr>
                    <th>Carrier ID</th>
                    <th>Carrier Name</th>
                    <th>30 Days Discount</th>
                    <th>60 Days Discount</th>
                    <th>90 Days Discount</th>
                    <th>Refund (2 Days)</th>
                    <th>Refund (10 Days)</th>
                    <th>Refund (20 Days)</th>
                    <th>Bulk Discount</th>
                    <th>Silver Discount</th>
                    <th>Gold Discount</th>
                    <th>Platinum Discount</th>
                    <th>Actions</th>
                </tr>
                <%
                    List<CarrierDetails> listCarriers = (List<CarrierDetails>) request.getAttribute("listCarriers");
                    if(listCarriers != null) {
                        for(CarrierDetails carrier : listCarriers) {
                %>
                <tr>
                    <td><%= carrier.getCarrierId() %></td>
                    <td><%= carrier.getCarrierName() %></td>
                    <td><%= carrier.getDiscountPercentageThirtyDaysAdvanceBooking() %>%</td>
                    <td><%= carrier.getDiscountPercentageSixtyDaysAdvanceBooking() %>%</td>
                    <td><%= carrier.getDiscountPercentageNinetyDaysAdvanceBooking() %>%</td>
                    <td><%= carrier.getRefundPercentageForTicketCancellation2DaysBeforeTravelDate() %>%</td>
                    <td><%= carrier.getRefundPercentageForTicketCancellation10DaysBeforeTravelDate() %>%</td>
                    <td><%= carrier.getRefundPercentageForTicketCancellation20DaysBeforeTravelDate() %>%</td>
                    <td><%= carrier.getBulkBookingDiscount() %>%</td>
                    <td><%= carrier.getSilverUserDiscount() %>%</td>
                    <td><%= carrier.getGoldUserDiscount() %>%</td>
                    <td><%= carrier.getPlatinumUserDiscount() %>%</td>
                    <td>
                        <button class="btn-edit" onclick="window.location.href='CarrierServlet?action=edit&id=<%= carrier.getCarrierId() %>'">
                            Edit
                        </button>
                        <button class="btn-delete" onclick="confirmDelete('<%= carrier.getCarrierId() %>')">
                            Delete
                        </button>
                    </td>
                </tr>
                <%
                        }
                    }
                %>
            </table>
        </div>
    </div>

   
    <jsp:include page="footer.jsp" />

    <script>
    function myFunction() {
        var input, filter, table, tr, td, i, j, txtValue;
        input = document.getElementById("myInput");
        filter = input.value.toUpperCase();
        table = document.getElementById("myTable");
        tr = table.getElementsByTagName("tr");

        for (i = 1; i < tr.length; i++) {
            
            tr[i].style.display = "none";
            td = tr[i].getElementsByTagName("td");
          
            for (j = 0; j < td.length; j++) {
                if (td[j]) {
                    txtValue = td[j].textContent || td[j].innerText;
                    if (txtValue.toUpperCase().indexOf(filter) > -1) {
                        tr[i].style.display = "";
                        break;
                    }
                }
            }
        }
    }


    function confirmDelete(id) {
        if (confirm('Are you sure?')) {
            window.location.href = 'CarrierServlet?action=delete&id=' + id;
        }
    }
    </script>

</body>
</html> 