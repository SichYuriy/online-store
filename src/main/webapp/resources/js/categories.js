/**
 * Created by Yuriy on 4/21/2017.
 */

function delete_ajax(id){
    $.ajax({
        url : '/admin/categories?id=' + id,
        type : 'DELETE',
        success : function(data) {
            if (data.redirect) {
                location.href = data.redirect;
            }
        }
    });
};