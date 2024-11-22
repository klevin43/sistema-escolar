$(document).ready(function() {
    $(".recomendacao").hide();
    $("#recomendacoes-check").click(function() {
        if(this.checked) {
            $(".recomendacao").show();
            return;
        }
        $(".recomendacao").hide();
    });
});
