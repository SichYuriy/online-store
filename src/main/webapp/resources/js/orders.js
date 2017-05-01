/**
 * Created by Yuriy on 4/23/2017.
 */
function overdue_ajax(id){
    $.ajax({
        url : '/admin/orders?orderStatus=OVERDUE&orderId=' + id,
        type : 'PUT',
        success : function(data) {
            if (data.redirect) {
                location.href = data.redirect;
            }
        }
    });
};

function cancel_ajax(id){
    $.ajax({
        url : '/user/orders?orderId=' + id,
        type : 'PUT',
        success : function(data) {
            if (data.redirect) {
                location.href = data.redirect;
            }
        }
    });
};

function pay_ajax(id){
    $.ajax({
        url : '/user/orders?orderId=' + id,
        type : 'POST',
        success : function(data) {
            if (data.redirect) {
                location.href = data.redirect;
            }
        }
    });
};