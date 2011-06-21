class Photo < ActiveRecord::Base
  #validates_presence_of :path, :thumbnail
  #validates_uniqueness_of :path, :thumbnail, :message => "There is already an image in the gallery with this same filename!" 
  belongs_to :user
  has_many :comments, :dependent => :destroy
  has_attached_file :image
end
