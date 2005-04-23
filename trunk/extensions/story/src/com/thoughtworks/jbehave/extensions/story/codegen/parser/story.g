class com.thoughtworks.jbehave.extensions.story.codegen.parser.StoryLexer extends Lexer;
options {k=2; testLiterals=false;}

TEXT
  options {testLiterals=true;}
  : ('a'..'z'|'A'..'Z') ('a'..'z'|'A'..'Z'|'0'..'9' | '_' | ':')*
  ;

NEWLINE
    : ( "\r\n"  // DOS/Windows
      | '\r'    // Macintosh
      | '\n'    // Unix
      )
      // increment the line count in the scanner
      { newline(); };

DOT : '.';

WS : (
    ' '
    | '\t'
    | '\f'){ $setType(Token.SKIP); };


class com.thoughtworks.jbehave.extensions.story.codegen.parser.AntlrStoryParser extends Parser;
options {buildAST=true;}

story       : titleDecl narrative (scenario)+ NEWLINE;
titleDecl   : "Story:" sentence;
narrative   : as_a i_want so_that;
as_a        : "As_a" sentence;
i_want      : "I_want" sentence;
so_that     : "So_that" sentence;
scenario    :  scenario_title context event outcome;
scenario_title : "Scenario:" sentence;
context     : "Given" sentence (and)*;
event       : "When" sentence;
outcome     : "Then" sentence (and)*;
and         : "and" sentence;
sentence    : (TEXT)+ NEWLINE;

class StoryTreeWalker extends TreeParser;

storyDetail :
    "Story:" sentence storyDetail
    | "As_a" sentence storyDetail
    | "I_want" sentence storyDetail
    | "So_that" sentence storyDetail
    | "Scenario:" sentence storyDetail
    | "Given" sentence storyDetail
    | "When" sentence storyDetail
    | "Then" sentence storyDetail
    | "and" sentence storyDetail;

sentence:
    txt:TEXT {System.out.print(txt.getText() + " ");} sentence
    | nwl:NEWLINE {System.out.println();};

