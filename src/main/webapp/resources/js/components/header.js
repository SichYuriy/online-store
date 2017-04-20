function logout() {
    $.post('/logout')
     .done(function(data, textStatus, jqXHR) {
         alert(${home})
        location.href = '/login.jsp';
     })
};
