class CreateComment2s < ActiveRecord::Migration
  def self.up
    create_table :comment2s do |t|
      t.string :title
      t.text :comment
      t.integer :user_id
      t.integer :photo_id

      t.timestamps
    end
  end

  def self.down
    drop_table :comment2s
  end
end
