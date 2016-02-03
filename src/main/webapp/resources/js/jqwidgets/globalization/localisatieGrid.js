/**
 * Created with JetBrains WebStorm.
 * User: fdmoor
 * Date: 12/6/12
 * Time: 5:06 PM
 * To change this template use File | Settings | File Templates.
 */


/*
define(['jquery','toonProject/toonKnoopDetails' ,'jqxtree','jsrender'], function ($,toonDetails) {
    'use strict';
*/


define( function () {


var localisatieGrid = {};


    localisatieGrid.localisatie = {
        // separator of parts of a date (e.g. '/' in 11/05/1955)
        '/': "/",
        // separator of parts of a time (e.g. ':' in 05:44 PM)
        ':': ":",
        // the first day of the week (0 = Sunday, 1 = Monday, etc)
        firstDay: 0,
        days: {
            // full day names
            names: ["Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"],
            // abbreviated day names
            namesAbbr: ["Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"],
            // shortest day names
            namesShort: ["Su", "Mo", "Tu", "We", "Th", "Fr", "Sa"]
        },
        months: {
            // full month names (13 months for lunar calendards -- 13th month should be "" if not lunar)
            names: ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December", ""],
            // abbreviated month names
            namesAbbr: ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec", ""]
        },
        // AM and PM designators in one of these forms:
        // The usual view, and the upper and lower case versions
        //      [standard,lowercase,uppercase]
        // The culture does not use AM or PM (likely all standard date formats use 24 hour time)
        //      null
        AM: ["AM", "am", "AM"],
        PM: ["PM", "pm", "PM"],
        eras: [
            // eras in reverse chronological order.
            // name: the name of the era in this culture (e.g. A.D., C.E.)
            // start: when the era starts in ticks (gregorian, gmt), null if it is the earliest supported era.
            // offset: offset in years from gregorian calendar
            {"name": "A.D.", "start": null, "offset": 0 }
        ],
        twoDigitYearMax: 2029,
        patterns: {
            // short date pattern
            d: "dd/MM/yyyy",
            // long date pattern
            D: "dddd, MMMM dd, yyyy",
            // short time pattern
            t: "h:mm tt",
            // long time pattern
            T: "h:mm:ss tt",
            // long date, short time pattern
            f: "dddd, MMMM dd, yyyy h:mm tt",
            // long date, long time pattern
            F: "dddd, MMMM dd, yyyy h:mm:ss tt",
            // month/day pattern
            M: "MMMM dd",
            // month/year pattern
            Y: "yyyy MMMM",
            // S is a sortable format that does not vary by culture
            S: "yyyy\u0027-\u0027MM\u0027-\u0027dd\u0027T\u0027HH\u0027:\u0027mm\u0027:\u0027ss"
        },
        percentsymbol: "%",
        currencysymbol: "€",
        currencysymbolposition: "after",
        decimalseparator: '.',
        thousandsseparator: '',
        pagergotopagestring: "ga naar pagina:",
        pagershowrowsstring: "toon rij :",
        pagerrangestring: " van ",
        pagerpreviousbuttonstring: "vorige",
        pagernextbuttonstring: "volgende",
        groupsheaderstring: "Drag a column and drop it here to group by that column",
        sortascendingstring: "Sort Ascending",
        sortdescendingstring: "Sort Descending",
        sortremovestring: "Remove Sort",
        groupbystring: "Group By this column",
        groupremovestring: "Remove from groups",
        filterclearstring: "Clear",
        filterstring: "Filter",
        filtershowrowstring: "Show rows where:",
        filterorconditionstring: "Or",
        filterandconditionstring: "And",
        filterselectallstring: "(Select All)",
        filterchoosestring: "Please Choose:",
        filterstringcomparisonoperators: ['empty', 'not empty', 'contains', 'contains(match case)',
            'does not contain', 'does not contain(match case)', 'starts with', 'starts with(match case)',
            'ends with', 'ends with(match case)', 'equal', 'equal(match case)', 'null', 'not null'],
        filternumericcomparisonoperators: ['equal', 'not equal', 'less than', 'less than or equal', 'greater than', 'greater than or equal', 'null', 'not null'],
        filterdatecomparisonoperators: ['equal', 'not equal', 'less than', 'less than or equal', 'greater than', 'greater than or equal', 'null', 'not null'],
        filterbooleancomparisonoperators: ['equal', 'not equal'],
        validationstring: "Entered value is not valid",
        emptydatastring: "Geen"
    };



    return localisatieGrid;



});

