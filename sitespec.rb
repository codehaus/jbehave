require 'rubygems'
require_gem 'BuildMaster'


class MySiteSpec < BuildMaster::SiteSpec
  def initialize
    root = BuildMaster::Cotta.new.file(__FILE__).parent
    @output_dir = root.dir('website/output')
    @content_dir = root.dir('website/content')
    @template_file = root.file('website/templates/skin.html')
    properties['release'] = '0.9'
    properties['prerelease'] = '0.9'
    properties['snapshot'] = '0.9'
  end

  def index_file?(content_path)
    return content_path.to_s =~ /index/
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
