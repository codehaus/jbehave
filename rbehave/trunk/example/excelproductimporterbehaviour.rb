# rbehave
require 'rbehave/core'

# domain classes
require 'example/excelproductimporter'
require 'rexml/document'

class ExcelProductImporterBehaviour < RBehave::Behaviour

  def should_write_vendor_entry
    # given
    xml = REXML::Document.new <<-END
      <Worksheet>
        <Row>
          <Cell>BrassCorp</Cell>
          <Cell>trumpet</Cell>
          <Cell>brass</Cell>
        </Row>
      </Worksheet>
    END
    
    vendor_writer = mock(VendorWriter)
    
    product_importer = ExcelProductImporter.new(vendor_writer)
    
    # expect
    vendor_writer.expects.write(1, 'BrassCorp')
    vendor_writer.expects.to_s
    
    # when
    product_importer.import(xml)

    # then
    ensure_that xml, contains("BrassCorp")
  end
end
