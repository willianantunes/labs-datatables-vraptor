$(document).ready(function(){
	
$.PSS = new Object();
$.PSS.language = window.custom_config_language;
$.PSS.custom_message_delete = window.custom_message_delete;
$.PSS.custom_message_edit = window.custom_message_edit;
$.PSS.custom_message_download = window.custom_message_download;
$.PSS.custom_message_upload = window.custom_message_upload;
$.PSS.custom_message_download_gen = window.custom_message_download_gen;
$.PSS.custom_message_yes = window.custom_message_yes;
$.PSS.custom_message_no = window.custom_message_no;
$.PSS.custom_message_activate_deactivate = window.custom_message_activate_deactivate;
$.PSS.custom_server_test_this_server = window.custom_server_test_this_server;
$.PSS.custom_opportunity_sendmail = window.custom_opportunity_sendmail;
$.PSS.url = window.custom_root_url;
	
// ------------------------------------
// ------------ Functions
// ------------------------------------	
	
	// http://joquery.com/2012/string-format-for-javascript
	String.format = function()
	{
	    // The string containing the format items (e.g. "{0}")
	    // will and always has to be the first argument.
	    var theString = arguments[0];
	    
	    // start with the second argument (i = 1)
	    for (var i = 1; i < arguments.length; i++) {
	        // "gm" = RegEx options for Global search (more than one instance)
	        // and for Multiline search
	        var regEx = new RegExp("\\{" + (i - 1) + "\\}", "gm");
	        theString = theString.replace(regEx, arguments[i]);
	    }
	    
	    return theString;
	};
	
	String.cut = function(text, maxLength)
	{
		if(text.length >= maxLength)
		{
			return text.substr(0, maxLength) + "...";
		}
		else
		{
			return text;
		}
	};
	
	$(".standard-form input[type=submit] + input[type=button]").click(function(event) {
	    event.preventDefault();
	    window.location.href = $.PSS.url;
	});
	
	
// ------------------------------------	
// ------------ Confirmation dialog
// ------------------------------------	
	
	// http://api.jqueryui.com/dialog/#option-buttons
	
	$("#___dialogConfirmation").dialog({
		autoOpen: false,
		modal: true
	});
	
	$(".___confirmLink").click(function(e) {
	    e.preventDefault();
	    var targetUrl = $(this).attr("href");

	    $("#___dialogConfirmation").dialog({
	      buttons : {
	        Yes : function() {
	          window.location.href = targetUrl;
	        },
	        Cancel : function() {
	          $(this).dialog("close");
	        }
	      }
	    });

	    $("#___dialogConfirmation").dialog("open");
	});
	
	$('#usersListDataTables').on('click', '.___confirmLink', function(e) {		
	    e.preventDefault();
	    var targetUrl = $(this).attr("href");

	    $("#___dialogConfirmation").dialog({
	      buttons : {
	        Yes : function() {
	          $(this).dialog("close");
	          window.location.href = targetUrl;
	        },
	        Cancel : function() {
	          $(this).dialog("close");
	        }
	      }
	    });	 

	    $("#___dialogConfirmation").dialog("open");		  
	});

// ------------------------------------	
// ------------ DataTables 1.10.2
// ------------------------------------	

	$.PSS.dataTables_languageFile = $.PSS.url + "js/data-tables/languages/dataTables." + $.PSS.language + ".json";
	$.PSS.dataTables_ajax_users = $.PSS.url + "users/json/datatables/paginate";	
	$.PSS.dataTables_search_user_name = window.custom_dataTables_search_name;

	$('#usersListDataTables').dataTable({
	    processing: true,
		serverSide: true,
		"language": {
			"url": $.PSS.dataTables_languageFile,
		    "search": $.PSS.dataTables_search_user_name
		},
		ajax: 
		{
		    url: $.PSS.dataTables_ajax_users,
		    type: "POST"
		},
		columns: [
		    {
		    	orderable: false,
		    	data: null,
		    	width: "69px",
		    	render: function(data, type, row, meta)
		    	{
		    		return "<a class=\"___confirmLink row-action-delete\" href='" + $.PSS.url + "users/delete/" + row[0] + "' title='" + $.PSS.custom_message_delete + "'></a>" +
		    				"<a class=\"row-action-edit\" href='" + $.PSS.url + "users/" + row[0] + "' title='" + $.PSS.custom_message_edit + "'></a>";
		    	}		    	
		    },
		    { 
		    	data: function(row, type, set, meta) 
		    	{ 
		    		return "<p title=\"" + row[1] +"\">" + String.cut(row[1], 40) + "</p>";
		    	} 
		    },
		    { 
		    	data: function(row, type, set, meta) 
		    	{ 
		    		return "<p title=\"" + row[2] +"\">" + String.cut(row[2], 40) + "</p>";
		    	} 
		    },		    
		    { data: function(row, type, set, meta) { return row[3]; } }
	    ],
	    order : [[ 1, 'asc' ]],
		"dom": 'T<"clear">lfrtip',
		"tableTools": {
            "sSwfPath": $.PSS.url + "swf/copy_csv_xls_pdf.swf",
            "aButtons": [
                         {
							"sExtends": "copy",
							"mColumns": [1, 2, 3, 4, 5, 6]
                         },
                         {
 							"sExtends": "csv",
 							"mColumns": [1, 2, 3, 4, 5, 6]
                         },
                         "print"
                     ]            
        }
	});
	
});

//------------------------------------	
//------------ Global variable
//------------------------------------	

var _popupWindow = null;
var _ajaxRequestObject = function(type, url, data, dataType, evBeforeSend, evSucess, evComplete, evError)
{
	$.ajax
	({
		type: type,
		url: url,
		data: data,
		dataType: dataType,
		beforeSend: evBeforeSend,
		success: evSucess,
		complete: evComplete,
		error: evError
	});
};

//------------------------------------	
//------------ Validations
//------------------------------------	

jQuery(document).ready(function(){
	jQuery("#___frmLogin").validationEngine();
	jQuery("#___frmUser").validationEngine();
	
	$('#___frmUser input[type=text]').tooltipster({
		iconDesktop: true,
		iconTouch: true,
		interactive: true,
		contentAsHTML: true
	});
	
	$('#___frmUser select').tooltipster({
		interactive: true,
		contentAsHTML: true
	});
});