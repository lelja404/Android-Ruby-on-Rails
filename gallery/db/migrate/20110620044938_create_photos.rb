class CreatePhotos < ActiveRecord::Migration
  def self.up
    create_table :photos do |t|
      t.string :title
      t.text :description
      t.string :filename
      t.string :content_type
      t.integer :size
      t.integer :width
      t.integer :height
      t.string :path
      t.string :thumbnail
      t.integer :user_id
      t.integer :photo_id

      t.timestamps
    end
  end

  def self.down
    drop_table :photos
  end
end
