require 'buildmaster'

class MySiteSpec < BuildMaster::SiteSpec
  def initialize
    root = File.dirname(__FILE__)
    @output_dir = File.join(root, 'website', 'output')
    @content_dir = File.join(root, 'website', 'content')
    @template_file = File.join(root, 'website', 'templates', 'skin.html')
  end

  def index_file?(content_path)
    return content_path =~ /index/
  end
  
  def not_index_file?(content_path)
    return ! index_file?(content_path)
  end
  
  def release
    return 'n/a'
  end
  
  def prerelease
    return 'n/a'
  end
  
  def snapshot
    return 'n/a'
  end
  
  def history(content_path)
    return content_path
  end
  
  def news_rss2
    return IO.read(File.join(@content_dir, 'news-rss2.xml'))
  end
end

site = BuildMaster::Site.new(MySiteSpec.new)
site.execute(ARGV)
