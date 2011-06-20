class Comment < ActiveRecord::Base
  validates :comment, :presence => true
  validates :user_id, :presence => true
  validates :photo_id, :presence => true
  belongs_to :user
  belongs_to :photo
end
