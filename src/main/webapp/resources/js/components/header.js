$('#logoutBtnId').click(function() {
    $.post('/logout')
     .done(function(data, textStatus, jqXHR) {
        location.href = '/login.jsp';
     })
});

function hoverSwap($el) {
    var swap = function(e) {
        var $span = $el.find('span');
        var currentText = $span.text();
        $span.text($el.data('alt'));
        $el.data('alt', currentText);
    };

    $el.hover(swap, swap);
}

$(function() {

    $('#lang').click(function(e) {
        e.preventDefault();
        $.post('/lang', {lang: $(this).text().trim()})
            .done(function() {
                location.reload();
            });
    });

    hoverSwap($("#lang"));
});