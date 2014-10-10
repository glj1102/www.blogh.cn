/*
 * For FCKeditor 2.3
 * 
 * File Name: fckplugin.js
 * 	Add a toolbar button to insert file.
 * 
 * File Authors:
 * 		Madpolice (madpolice_dong@163.com) 20060726
 */


// Register the related commands.
FCKCommands.RegisterCommand( 'FileUpload'		, new FCKDialogCommand( FCKLang['DlgFileUploadTitle']	, FCKLang['DlgFileUploadTitle']		, FCKConfig.PluginsPath + 'fileupload/file.html'	, 450, 350 ) ) ;

// Create the "Media" toolbar button.
var oFindItem		= new FCKToolbarButton( 'FileUpload', FCKLang['FileUploadInsertMedia'] ) ;
oFindItem.IconPath	= FCKConfig.PluginsPath + 'fileupload/file.gif' ;

FCKToolbarItems.RegisterItem( 'FileUpload', oFindItem ) ;			// 'FileUpload' is the name used in the Toolbar config.
