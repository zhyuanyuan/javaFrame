$(document).ready(function(){
    //focus username field
    $("input:visible:enabled:first").focus();
    //flash error box
    $('#msg.errors').animate({ backgroundColor: 'rgb(187,0,0)' }, 80).animate({ backgroundColor: 'rgb(255,238,221)' }, 800);

    //flash success box
    $('#msg.success').animate({ backgroundColor: 'rgb(51,204,0)' }, 50).animate({ backgroundColor: 'rgb(221,255,170)' }, 500);
    
    //flash confirm box
    $('#msg.question').animate({ backgroundColor: 'rgb(51,204,0)' }, 50).animate({ backgroundColor: 'rgb(221,255,170)' }, 500);
});