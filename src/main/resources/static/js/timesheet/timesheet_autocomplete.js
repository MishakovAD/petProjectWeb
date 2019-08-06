function autocompleteMovie() {
    $(function(){
        $('input#query').autocomplete({
            source: available_movie
        });
    });
}

function autocompleteSuggestMovie() {
    var haystack = ["ActionScript", "AppleScript", "Asp", "BASIC"];
    $(function(){
        $('input#query').suggest(haystack, {
            suggestionColor   : '#cccccc',
            moreIndicatorClass: 'suggest-more',
            moreIndicatorText : '&hellip;'
        });
    });
}