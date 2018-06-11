
jQuery(document).ready(function() {
    $.backstretch("image/background/1.jpg");
    $('.registration-form fieldset:first-child').fadeIn('slow');
    
    $('.registration-form input[type="text"], .registration-form input[type="password"], .registration-form textarea').on('focus', function() {
    	$(this).removeClass('input-error');
    });
    
    $('.registration-form .btn-next').on('click', function() {
    	var parent_fieldset = $(this).parents('fieldset');
    	var next_step = true;
    	parent_fieldset.find('input[type="text"], input[type="password"], textarea').each(function() {
    		if( $(this).val() == "" ) {
    			$(this).addClass('input-error');
    			next_step = false;
    		}
    		else {
    			$(this).removeClass('input-error');
    		}
    	});

        var pwd=$('#form-password').val();
        var re_pwd=$('#form-repeat-password').val();
        if (pwd!=re_pwd){
            next_step=false;
            $('#form-password').addClass('input-error');
            $('#form-repeat-password').addClass('input-error');
        }

        var email=$('#form-email').val();
        if (email.indexOf("@")<0) {
            next_step=false;
            $('#form-email').addClass('input-error');
        }

    	if( next_step ) {
    		parent_fieldset.fadeOut(400, function() {
	    		$(this).next().fadeIn();
	    	});
    	}
    });
    
    $('.registration-form .btn-previous').on('click', function() {
    	$(this).parents('fieldset').fadeOut(400, function() {
    		$(this).prev().fadeIn();
    	});
    });
    
    $('.registration-form').on('submit', function(e) {
    	var valid=true;
        $(this).find('input[type="text"], input[type="password"], textarea').each(function() {
            if( $(this).val() == "" ) {
                valid=false;
                $(this).addClass('input-error');
            }
            else {
                $(this).removeClass('input-error');
            }
        });
		if (!valid) e.preventDefault();
    });
});
