var age = 999;
var today = new Date();
var nowyear = today.getFullYear();
var nowmonth = today.getMonth() + 1; //we add 1 because getMonth is zero indexed
var nowday = today.getDate();

jQuery(function() {
    var birthdateYearField = jQuery('#birthdateYear-field');
    var birthdateMonthField = jQuery('#birthdateMonth-field');
    var birthdateDayField = jQuery('#birthdateDay-field');
    var yearsField = jQuery('#birthdateYears-field');
    var monthsField = jQuery('#birthdateMonths-field');

    jQuery(yearsField.change(function() {
        if((!!monthsField.val() != '' && yearsField.val() == '') || (!!yearsField.val() && yearsField.val() < 18)) {
            NavigatorController.getSectionById('powerOfAttorneyContact').show();
        } else {
            NavigatorController.getSectionById('powerOfAttorneyContact').hide();
        }
    }));

    jQuery(monthsField.change(function() {
        if((!!monthsField.val() != '' && yearsField.val() == '') || (!!yearsField.val() && yearsField.val() < 18)) {
            NavigatorController.getSectionById('powerOfAttorneyContact').show();
        } else {
            NavigatorController.getSectionById('powerOfAttorneyContact').hide();
        }
    }));

    jQuery(birthdateYearField.change(function() {
        if (!!birthdateYear && !!birthdateMonth && !!birthdateDay) {
            togglePowerOfAttorneyContact();
        }
    }));

    jQuery(birthdateMonthField.change(function() {
        if (!!birthdateYear && !!birthdateMonth && !!birthdateDay) {
            togglePowerOfAttorneyContact();
        }
    }));

    jQuery(birthdateDayField.change(function() {
        if (!!birthdateYear && !!birthdateMonth && !!birthdateDay) {
            togglePowerOfAttorneyContact();
        }
    }));
});

jQuery(document).ready(function() {
    NavigatorController.getSectionById('powerOfAttorneyContact').hide();
});

function togglePowerOfAttorneyContact() {
    var birthdateYear = jQuery('#birthdateYear-field').val();
    age = nowyear - birthdateYear;
    var age_month = nowmonth - birthdateMonth;
    var age_day = nowday - birthdateDay;
   
    if(age_month < 0 || (age_month == 0 && age_day <0)) {
            age = parseInt(age) -1;
        }

    if(age < 18) {
        NavigatorController.getSectionById('powerOfAttorneyContact').show();
    } else {
        NavigatorController.getSectionById('powerOfAttorneyContact').hide();
    }
};