/**
 * Created by Yuriy on 4/23/2017.
 */
function overdue_ajax(id){
    $.ajax({
        url : '/admin/orders?orderStatus=OVERDUE&id=' + id,
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
        url : '/user/orders?id=' + id,
        type : 'PUT',
        success : function(data) {
            if (data.redirect) {
                location.href = data.redirect;
            }
        }
    });
};