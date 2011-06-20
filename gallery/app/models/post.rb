class Post < ActiveRecord::Base
  belongs_to :user
 # has_attachment :storage => :file_system,
 # :thumbnails => { :thumb => '160>' },
 # :content_type => :image
 # validates_as_attachment

end
