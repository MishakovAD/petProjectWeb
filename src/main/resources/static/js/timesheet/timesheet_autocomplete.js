function autocompleteMovie() {
    $(function(){
        $('input#movieName').autocomplete({
            source: available_movie
        });
    });
}

function autocompleteSuggestMovie() {
    var haystack = available_movie;
    $(function(){
        $('input#movieName').suggest(haystack, {
            suggestionColor   : '#cccccc',
            moreIndicatorClass: 'suggest-more',
            moreIndicatorText : '&hellip;'
        });
        $('#suggestDiv.col-3 div').css('padding-bottom', '0em');
    });
}