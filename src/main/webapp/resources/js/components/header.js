$('#logoutBtnId').click(function() {
    $.post('/logout')
     .done(function(data, textStatus, jqXHR) {
        location.href = '/login.jsp';
     })
});
