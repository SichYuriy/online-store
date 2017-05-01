/**
 * Created by Yuriy on 5/1/2017.
 */

function toCart(productId) {
    $.ajax({
        url : '/user/cartItems?productId=' + productId,
        type : 'POST',
        success : function(data) {
            if (data.error) {
                document.getElementById("add_error_alert").style.display = "block";
                setTimeout(function() {
                    document.getElementById("add_error_alert").style.display = "none";
                }, 2500);
            } else {
                document.getElementById("add_success_alert").style.display = "block";
                setTimeout(function() {
                    document.getElementById("add_success_alert").style.display = "none";
                }, 2500);
            }

        }
    });

}