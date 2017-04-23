/**
 * Created by Yuriy on 4/23/2017.
 */
function update_ajax(id, blackList){
    $.ajax({
        url : '/admin/users?id=' + id + '&blackList=' + blackList,
        type : 'PUT',
        success : function(data) {
            if (data.redirect) {
                location.href = data.redirect;
            }
        }
    });
};