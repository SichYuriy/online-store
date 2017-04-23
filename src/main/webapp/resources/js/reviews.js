/**
 * Created by Yuriy on 4/23/2017.
 */
function delete_ajax(id){
    $.ajax({
        url : '/user/reviews?id=' + id,
        type : 'DELETE',
        success : function(data) {
            if (data.redirect) {
                location.href = data.redirect;
            }
        }
    });
};