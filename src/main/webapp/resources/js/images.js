/**
 * Created by Yuriy on 5/1/2017.
 */
function delete_ajax(imageId, productId){
    $.ajax({
        url : '/admin/images?productImageId=' + imageId,
        type : 'DELETE',
        success : function(data) {
            location.reload(true);
        }
    });
};