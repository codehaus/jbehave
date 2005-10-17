require 'rbehave/core'
require 'rexml/document'

include RBehave

class ExcelProductImporterBehaviour

  include Constraints
  include Verifiers
  
  def should_write_vendor_entry
    # given...
    xml = REXML::Document.new <<-END
      <Worksheet>
        <Row>
          <Cell>BrassCorp</Cell>
          <Cell>trumpet</Cell>
          <Cell>brass</Cell>
        </Row>
      </Worksheet>
    END
    
    vendor_writer = verifier_for(VendorWriter)
    
    product_importer = ExcelProductImporter.new(vendor_writer)
    
    # expect...
    vendor_writer.__should_receive { |mock|
      mock.write(1, 'BrassCorp')
    }
    
    # when...
    
    ensure_that xml, contains("BrassCorp")
  end
end
