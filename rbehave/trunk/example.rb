require 'rbehave/core'
require 'rexml/document'

include RBehave

class ExcelProductImporterBehaviour < UsingConstraints

  include REXML
  
  def should_do_something
    xml = <<-END
      <Worksheet>
        <Row>
          <Cell>BrassCorp</Cell>
          <Cell>trumpet</Cell>
          <Cell>brass</Cell>
        </Row>
      </Worksheet>
    END
    
    doc = Document.new xml
    
    ensure_that doc.to_s, contains('BrassCorp')
  end
end
