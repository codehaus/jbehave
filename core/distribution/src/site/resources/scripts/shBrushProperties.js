dp.sh.Brushes.Properties = function()
{
	this.regexList = [
		{ regex: dp.sh.RegexLib.SingleLinePerlComments,							css: 'comment' },		// one line comments
		{ regex: new RegExp('\\b([\\d]+(\\.[\\d]+)?|0x[a-f0-9]+)\\b', 'gi'),	css: 'number' },		// numbers
	];
}

dp.sh.Brushes.Properties.prototype	= new dp.sh.Highlighter();
dp.sh.Brushes.Properties.Aliases	= ['properties'];

