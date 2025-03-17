<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.model.CarrierDetails" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Carrier Form</title>
    <style>
        .register-container {
            display: flex;
            align-items: center;
            justify-content: center;
            flex-direction: column;
            padding: 5px;
            height: 80%;
            width: 30%;
            overflow-y: auto;
            padding-right: 20px;
            background: rgb(220, 220, 220);
            border-radius: 10px;
            box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.5);
            margin: auto;
            text-align: center;
        }

        h2 {
            color: black;
        }

        input, textarea {
            width: 90%;
            padding: 10px;
            margin: 10px;
            border: 1px solid black;
            border-radius: 5px;
        }

        .register-btn {
            margin-top: 10px;
            width: 50%;
            padding: 10px;
            background-color: dodgerblue;
            color: white;
            font-size: 18px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }

        .register-btn:hover {
            background-color: cobalt;
        }

        .reset-btn {
            width: 40%;
            padding: 10px;
            background-color: gray;
            color: white;
            font-size: 18px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }

        #acknowledgment {
            margin-top: 15px;
            color: green;
            display: none;
        }

        a {
            text-decoration: none;
            color: white;
        }
    </style>
    <script>
        function validateForm() {
            const fields = ["discount30", "discount60", "discount90", "refund2", "refund10", "refund20", "bulkDiscount", "silverDiscount", "goldDiscount", "platinumDiscount"];
            for (let field of fields) {
                let value = document.forms["carrierForm"][field].value;
                if (value < 0 || value > 100) {
                    alert(field.replace(/([A-Z])/g, ' $1') + " must be between 0 and 100.");
                    return false;
                }
            }
            return true;
        }
    </script>
</head>
<body style="background-color: teal;">
    <jsp:include page="adminheader.jsp"></jsp:include>
    <%
        CarrierDetails carrier = (CarrierDetails) request.getAttribute("carrier");
        boolean isEdit = (carrier != null);
    %>
    <div class="register-container">
        <h2><%= isEdit ? "Edit Carrier" : "Add New Carrier" %></h2>
        
        <form name="carrierForm" action="CarrierServlet" method="post" onsubmit="return validateForm()">
            <% if (isEdit) { %>
                <input type="hidden" name="action" value="update">
                <input type="hidden" name="id" value="<%= carrier.getCarrierId() %>">
            <% } else { %>
                <input type="hidden" name="action" value="insert">
            <% } %>
            <label>Carrier Name:</label>
            <input type="text" name="carrierName" value="<%= isEdit ? carrier.getCarrierName() : "" %>" required><br>
            
            <label>30 Days Discount (%):</label>
            <input type="number" name="discount30" value="<%= isEdit ? carrier.getDiscountPercentageThirtyDaysAdvanceBooking() : "" %>" required><br>
            
            <label>60 Days Discount (%):</label>
            <input type="number" name="discount60" value="<%= isEdit ? carrier.getDiscountPercentageSixtyDaysAdvanceBooking() : "" %>" required><br>
            
            <label>90 Days Discount (%):</label>
            <input type="number" name="discount90" value="<%= isEdit ? carrier.getDiscountPercentageNinetyDaysAdvanceBooking() : "" %>" required><br>
            
            <label>Refund (2 Days Before) (%):</label>
            <input type="number" name="refund2" value="<%= isEdit ? carrier.getRefundPercentageForTicketCancellation2DaysBeforeTravelDate() : "" %>" required><br>
            
            <label>Refund (10 Days Before) (%):</label>
            <input type="number" name="refund10" value="<%= isEdit ? carrier.getRefundPercentageForTicketCancellation10DaysBeforeTravelDate() : "" %>" required><br>
            
            <label>Refund (20 Days Before) (%):</label>
            <input type="number" name="refund20" value="<%= isEdit ? carrier.getRefundPercentageForTicketCancellation20DaysBeforeTravelDate() : "" %>" required><br>
            
            <label>Bulk Booking Discount (%):</label>
            <input type="number" name="bulkDiscount" value="<%= isEdit ? carrier.getBulkBookingDiscount() : "" %>" required><br>
            
            <label>Silver User Discount (%):</label>
            <input type="number" name="silverDiscount" value="<%= isEdit ? carrier.getSilverUserDiscount() : "" %>" required><br>
            
            <label>Gold User Discount (%):</label>
            <input type="number" name="goldDiscount" value="<%= isEdit ? carrier.getGoldUserDiscount() : "" %>" required><br>
            
            <label>Platinum User Discount (%):</label>
            <input type="number" name="platinumDiscount" value="<%= isEdit ? carrier.getPlatinumUserDiscount() : "" %>" required><br>
            
            <input class="register-btn" type="submit" value="Submit">
        </form>
        <br>
        <button onclick="window.location.href='CarrierServlet?action=list'" class="register-btn" >Back to Carrier List</button>
    </div>
    <jsp:include page="footer.jsp"></jsp:include>
</body>
</html>
