/**
 * Created with IntelliJ IDEA.
 * User: Asus
 * Date: 24.08.13
 * Time: 7:25
 * To change this template use File | Settings | File Templates.
 */
//
//	jQuery Validate example script
//
//	Prepared by David Cochran
//
//	Free for your use -- No warranties, no guarantees!
//

$(document).ready(function () {
    // Validate
    // http://bassistance.de/jquery-plugins/jquery-plugin-validation/
    // http://docs.jquery.com/Plugins/Validation/
    // http://docs.jquery.com/Plugins/Validation/validate#toptions

    $('#client-details-block').validate({
        rules: {
            inputName: {
                minlength: 5,
                required: true
            },
            inputSurName: {
                required: true,
                minlength: 5
            },
            inputLastName: {
                minlength: 5,
                required: true
            },
            inputPhone: {
                minlength: 5,
                required: true
            },
            name: {
                minlength: 5,
                required: true
            }
        },
        highlight: function (element) {
            $(element).closest('.controls').removeClass('success').addClass('error');
        },
        success: function (element) {
            element.text('OK!').addClass('valid')
                .closest('.controls').removeClass('error').addClass('success');
        }

    });
    $(document).oneTime("1s", function () {
        $("#client-list-block").tablesorter();
        $("#client-list-block").oneSimpleTablePagination({rowsPerPage: 3});
    })
});